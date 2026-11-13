package com.tariqnaeemdeveloper.colorlink.data

import android.content.Context
import android.content.SharedPreferences
import com.tariqnaeemdeveloper.colorlink.model.Difficulty

import androidx.core.content.edit

class ProgressRepository(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("color_link_progress", Context.MODE_PRIVATE)

    fun getLevelStars(levelId: String): Int {
        return prefs.getInt("stars_$levelId", 0)
    }

    fun isLevelCompleted(levelId: String): Boolean {
        return prefs.getBoolean("completed_$levelId", false)
    }

    fun getBestMoves(levelId: String): Int {
        return prefs.getInt("moves_$levelId", Int.MAX_VALUE)
    }

    fun saveLevelProgress(levelId: String, stars: Int, moves: Int) {
        val currentStars = getLevelStars(levelId)
        val currentBestMoves = getBestMoves(levelId)

        val newStars = Math.max(currentStars, stars)
        val newMoves = Math.min(currentBestMoves, moves)

        prefs.edit {
            putInt("stars_$levelId", newStars)
            putBoolean("completed_$levelId", true)
            putInt("moves_$levelId", newMoves)
        }
    }

    fun getTotalStarsForDifficulty(difficulty: Difficulty): Int {
        val levels = LevelRepository.getLevelsForDifficulty(difficulty)
        return levels.sumOf { getLevelStars(it.id) }
    }

    fun getTotalStars(): Int {
        val allLevels = Difficulty.entries.flatMap { LevelRepository.getLevelsForDifficulty(it) }
        return allLevels.sumOf { getLevelStars(it.id) }
    }

    fun isLevelUnlocked(difficulty: Difficulty, levelNumber: Int): Boolean {
        if (levelNumber <= 1) return true
        val previousLevelId = "${difficulty.id}_${levelNumber - 1}"
        return isLevelCompleted(previousLevelId)
    }

    fun isDarkTheme(): Boolean {
        return prefs.getBoolean("is_dark_theme", true)
    }

    fun setDarkTheme(isDark: Boolean) {
        prefs.edit { putBoolean("is_dark_theme", isDark) }
    }
}
