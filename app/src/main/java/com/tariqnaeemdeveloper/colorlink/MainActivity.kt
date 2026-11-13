package com.tariqnaeemdeveloper.colorlink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.tariqnaeemdeveloper.colorlink.model.Difficulty
import com.tariqnaeemdeveloper.colorlink.ui.screens.GameScreen
import com.tariqnaeemdeveloper.colorlink.ui.screens.HomeScreen
import com.tariqnaeemdeveloper.colorlink.ui.screens.LevelSelectScreen
import com.tariqnaeemdeveloper.colorlink.ui.screens.SplashScreen
import com.tariqnaeemdeveloper.colorlink.ui.theme.ColorLinkTheme
import com.tariqnaeemdeveloper.colorlink.ui.viewmodel.GameViewModel

sealed class Screen {
    object Splash : Screen()
    object Home : Screen()
    data class LevelSelect(val difficulty: Difficulty) : Screen()
    data class Game(val levelId: String) : Screen()
}

class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsState()
            ColorLinkTheme(darkTheme = uiState.isDarkTheme) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ColorLinkApp(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ColorLinkApp(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Splash) }
    val uiState by viewModel.uiState.collectAsState()

    when (val screen = currentScreen) {
        is Screen.Splash -> {
            SplashScreen(
                isDarkTheme = uiState.isDarkTheme,
                onSplashFinished = {
                    currentScreen = Screen.Home
                },
                modifier = modifier
            )
        }

        is Screen.Home -> {
            HomeScreen(
                totalStars = uiState.totalStars,
                isDarkTheme = uiState.isDarkTheme,
                onToggleTheme = { viewModel.toggleTheme() },
                onStartGame = { difficulty ->
                    viewModel.selectDifficulty(difficulty)
                    currentScreen = Screen.LevelSelect(difficulty)
                },
                modifier = modifier
            )
        }

        is Screen.LevelSelect -> {
            LevelSelectScreen(
                selectedDifficulty = screen.difficulty,
                progressRepository = viewModel.progressRepository,
                onDifficultySelected = { difficulty ->
                    viewModel.selectDifficulty(difficulty)
                    currentScreen = Screen.LevelSelect(difficulty)
                },
                onLevelSelected = { level ->
                    viewModel.loadLevel(level.id)
                    currentScreen = Screen.Game(level.id)
                },
                onBackClick = {
                    viewModel.refreshTotalStars()
                    currentScreen = Screen.Home
                },
                modifier = modifier
            )
        }

        is Screen.Game -> {
            GameScreen(
                viewModel = viewModel,
                onBackClick = {
                    currentScreen = Screen.LevelSelect(uiState.selectedDifficulty)
                },
                onLevelSelectClick = {
                    currentScreen = Screen.LevelSelect(uiState.selectedDifficulty)
                },
                modifier = modifier
            )
        }
    }
}
