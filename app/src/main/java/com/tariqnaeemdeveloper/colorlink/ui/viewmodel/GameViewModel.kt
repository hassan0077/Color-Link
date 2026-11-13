package com.tariqnaeemdeveloper.colorlink.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tariqnaeemdeveloper.colorlink.data.LevelRepository
import com.tariqnaeemdeveloper.colorlink.data.ProgressRepository
import com.tariqnaeemdeveloper.colorlink.engine.ColorLinkEngine
import com.tariqnaeemdeveloper.colorlink.model.Difficulty
import com.tariqnaeemdeveloper.colorlink.model.Level
import com.tariqnaeemdeveloper.colorlink.model.PathState
import com.tariqnaeemdeveloper.colorlink.model.Position
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GameUiState(
    val currentLevel: Level? = null,
    val paths: Map<Int, PathState> = emptyMap(),
    val moves: Int = 0,
    val coveragePercentage: Int = 0,
    val isAllConnected: Boolean = false,
    val isVictory: Boolean = false,
    val starsEarned: Int = 0,
    val elapsedTimeSeconds: Int = 0,
    val canUndo: Boolean = false,
    val selectedDifficulty: Difficulty = Difficulty.EASY,
    val totalStars: Int = 0,
    val hintsRemaining: Int = 3,
    val hintMessage: String? = null,
    val isDarkTheme: Boolean = true
)

class GameViewModel(application: Application) : AndroidViewModel(application) {

    val progressRepository = ProgressRepository(application)

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var engine: ColorLinkEngine? = null
    private var timerJob: Job? = null

    init {
        refreshTotalStars()
        _uiState.update { it.copy(isDarkTheme = progressRepository.isDarkTheme()) }
    }

    fun toggleTheme() {
        val newTheme = !_uiState.value.isDarkTheme
        progressRepository.setDarkTheme(newTheme)
        _uiState.update { it.copy(isDarkTheme = newTheme) }
    }

    fun refreshTotalStars() {
        val stars = progressRepository.getTotalStars()
        _uiState.update { it.copy(totalStars = stars) }
    }

    fun selectDifficulty(difficulty: Difficulty) {
        _uiState.update { it.copy(selectedDifficulty = difficulty) }
    }

    fun loadLevel(levelId: String) {
        val level = LevelRepository.getLevelById(levelId) ?: return
        val newEngine = ColorLinkEngine(level)
        engine = newEngine

        timerJob?.cancel()
        _uiState.update {
            it.copy(
                currentLevel = level,
                paths = newEngine.paths,
                moves = 0,
                coveragePercentage = 0,
                isAllConnected = false,
                isVictory = false,
                starsEarned = 0,
                elapsedTimeSeconds = 0,
                canUndo = false,
                selectedDifficulty = level.difficulty,
                hintsRemaining = 3,
                hintMessage = null
            )
        }

        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                if (_uiState.value.isVictory) break
                _uiState.update { it.copy(elapsedTimeSeconds = it.elapsedTimeSeconds + 1) }
            }
        }
    }

    fun onCellTouchDown(pos: Position) {
        val eng = engine ?: return
        if (_uiState.value.isVictory) return

        eng.onCellTouchDown(pos)
        updateStateFromEngine(eng)
    }

    fun onCellTouchMove(pos: Position) {
        val eng = engine ?: return
        if (_uiState.value.isVictory) return

        eng.onCellTouchMove(pos)
        updateStateFromEngine(eng)
    }

    fun onCellTouchUp() {
        val eng = engine ?: return
        if (_uiState.value.isVictory) return

        eng.onCellTouchUp()
        updateStateFromEngine(eng)

        checkVictory(eng)
    }

    fun undo() {
        val eng = engine ?: return
        if (_uiState.value.isVictory) return

        eng.undo()
        updateStateFromEngine(eng)
    }

    fun useHint() {
        val eng = engine ?: return
        if (_uiState.value.isVictory) return
        if (_uiState.value.hintsRemaining <= 0) {
            _uiState.update { it.copy(hintMessage = "No hints remaining!") }
            return
        }

        val applied = eng.applyHint()
        if (applied) {
            updateStateFromEngine(eng)
            _uiState.update {
                it.copy(
                    hintsRemaining = it.hintsRemaining - 1,
                    hintMessage = "Hint applied!"
                )
            }
            checkVictory(eng)
        } else {
            _uiState.update { it.copy(hintMessage = "All pairs already solved!") }
        }
    }

    fun clearHintMessage() {
        _uiState.update { it.copy(hintMessage = null) }
    }

    fun resetLevel() {
        val eng = engine ?: return
        eng.reset()
        updateStateFromEngine(eng)
        _uiState.update { it.copy(isVictory = false, starsEarned = 0) }
    }

    private fun updateStateFromEngine(eng: ColorLinkEngine) {
        _uiState.update {
            it.copy(
                paths = eng.paths,
                moves = eng.moveCount,
                coveragePercentage = eng.getCoveragePercentage(),
                isAllConnected = eng.isAllConnected(),
                canUndo = eng.canUndo()
            )
        }
    }

    private fun checkVictory(eng: ColorLinkEngine) {
        if (eng.isLevelCompleted() && !_uiState.value.isVictory) {
            val stars = eng.calculateStars()
            val currentLvl = eng.level

            progressRepository.saveLevelProgress(currentLvl.id, stars, eng.moveCount)
            refreshTotalStars()

            _uiState.update {
                it.copy(
                    isVictory = true,
                    starsEarned = stars
                )
            }
        }
    }

    fun getNextLevelId(): String? {
        val current = _uiState.value.currentLevel ?: return null
        val levelsInDifficulty = LevelRepository.getLevelsForDifficulty(current.difficulty)
        val currentIndex = levelsInDifficulty.indexOfFirst { it.id == current.id }
        if (currentIndex in 0 until levelsInDifficulty.size - 1) {
            return levelsInDifficulty[currentIndex + 1].id
        }
        return null
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
