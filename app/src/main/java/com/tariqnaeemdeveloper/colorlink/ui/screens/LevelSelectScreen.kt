package com.tariqnaeemdeveloper.colorlink.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tariqnaeemdeveloper.colorlink.data.LevelRepository
import com.tariqnaeemdeveloper.colorlink.data.ProgressRepository
import com.tariqnaeemdeveloper.colorlink.model.Difficulty
import com.tariqnaeemdeveloper.colorlink.model.Level

@Composable
fun LevelSelectScreen(
    selectedDifficulty: Difficulty,
    progressRepository: ProgressRepository,
    onDifficultySelected: (Difficulty) -> Unit,
    onLevelSelected: (Level) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val levels = LevelRepository.getLevelsForDifficulty(selectedDifficulty)
    val bgColor = MaterialTheme.colorScheme.background
    val surfaceColor = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onBackground

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(top = 16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(surfaceColor)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = textColor
                )
            }

            Text(
                text = "Select Level",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = textColor,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Difficulty Tabs
        ScrollableTabRow(
            selectedTabIndex = Difficulty.entries.indexOf(selectedDifficulty),
            containerColor = Color.Transparent,
            contentColor = textColor,
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                val index = Difficulty.entries.indexOf(selectedDifficulty)
                if (index in tabPositions.indices) {
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[index]),
                        color = Color(0xFF007AFF)
                    )
                }
            }
        ) {
            Difficulty.entries.forEach { diff ->
                Tab(
                    selected = diff == selectedDifficulty,
                    onClick = { onDifficultySelected(diff) },
                    text = {
                        Text(
                            text = diff.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = if (diff == selectedDifficulty) FontWeight.Bold else FontWeight.Normal
                            ),
                            color = if (diff == selectedDifficulty) textColor else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Level Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(levels) { level ->
                val isUnlocked = progressRepository.isLevelUnlocked(level.difficulty, level.levelNumber)
                val stars = progressRepository.getLevelStars(level.id)

                LevelTile(
                    level = level,
                    isUnlocked = isUnlocked,
                    stars = stars,
                    onClick = { if (isUnlocked) onLevelSelected(level) }
                )
            }
        }
    }
}

@Composable
private fun LevelTile(
    level: Level,
    isUnlocked: Boolean,
    stars: Int,
    onClick: () -> Unit
) {
    val unlockedColor = MaterialTheme.colorScheme.surface
    val lockedColor = MaterialTheme.colorScheme.surfaceVariant
    val textColor = MaterialTheme.colorScheme.onSurface

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp))
            .clickable(enabled = isUnlocked, onClick = onClick),
        color = if (isUnlocked) unlockedColor else lockedColor,
        shadowElevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isUnlocked) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${level.levelNumber}",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        for (i in 1..3) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star $i",
                                modifier = Modifier.size(16.dp),
                                tint = if (i <= stars) Color(0xFFFFCC00) else Color(0xFF94A3B8)
                            )
                        }
                    }
                }
            } else {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}
