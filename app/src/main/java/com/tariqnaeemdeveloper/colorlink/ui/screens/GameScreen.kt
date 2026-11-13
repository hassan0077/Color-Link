package com.tariqnaeemdeveloper.colorlink.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.tariqnaeemdeveloper.colorlink.ui.components.ColorLinkBoard
import com.tariqnaeemdeveloper.colorlink.ui.components.GameHeaderBar
import com.tariqnaeemdeveloper.colorlink.ui.components.VictoryDialog
import com.tariqnaeemdeveloper.colorlink.ui.viewmodel.GameViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onBackClick: () -> Unit,
    onLevelSelectClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val level = uiState.currentLevel
    val snackbarHostState = remember { SnackbarHostState() }
    val haptic = LocalHapticFeedback.current

    LaunchedEffect(uiState.hintMessage) {
        val msg = uiState.hintMessage
        if (msg != null) {
            snackbarHostState.showSnackbar(msg)
            viewModel.clearHintMessage()
        }
    }

    LaunchedEffect(uiState.isVictory) {
        if (uiState.isVictory) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Header Bar
            GameHeaderBar(
                level = level,
                moves = uiState.moves,
                coveragePercentage = uiState.coveragePercentage,
                elapsedSeconds = uiState.elapsedTimeSeconds,
                canUndo = uiState.canUndo,
                hintsRemaining = uiState.hintsRemaining,
                onBackClick = onBackClick,
                onUndoClick = { viewModel.undo() },
                onResetClick = { viewModel.resetLevel() },
                onHintClick = { viewModel.useHint() }
            )

            // Game Board
            if (level != null) {
                ColorLinkBoard(
                    level = level,
                    paths = uiState.paths,
                    onCellTouchDown = { viewModel.onCellTouchDown(it) },
                    onCellTouchMove = { viewModel.onCellTouchMove(it) },
                    onCellTouchUp = { viewModel.onCellTouchUp() }
                )
            }

            // Bottom Hints / Instruction
            Text(
                text = if (uiState.isAllConnected) "Board Connected! Fill remaining cells for 100%" else "Connect matching color pairs",
                style = MaterialTheme.typography.bodyMedium,
                color = if (uiState.isAllConnected) Color(0xFF34C759) else Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // Snackbar Host for Hint Messages
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )

        // Victory Dialog
        if (uiState.isVictory) {
            val nextLevelId = viewModel.getNextLevelId()
            VictoryDialog(
                starsEarned = uiState.starsEarned,
                moves = uiState.moves,
                coveragePercentage = uiState.coveragePercentage,
                elapsedSeconds = uiState.elapsedTimeSeconds,
                hasNextLevel = nextLevelId != null,
                onNextLevelClick = {
                    if (nextLevelId != null) {
                        viewModel.loadLevel(nextLevelId)
                    }
                },
                onReplayClick = { viewModel.resetLevel() },
                onLevelSelectClick = onLevelSelectClick
            )
        }
    }
}
