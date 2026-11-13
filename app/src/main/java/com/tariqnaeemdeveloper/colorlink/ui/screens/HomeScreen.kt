package com.tariqnaeemdeveloper.colorlink.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tariqnaeemdeveloper.colorlink.model.Difficulty
import com.tariqnaeemdeveloper.colorlink.model.DotColor

@Composable
fun HomeScreen(
    totalStars: Int,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onStartGame: (Difficulty) -> Unit,
    modifier: Modifier = Modifier
) {
    var showHowToPlay by remember { mutableStateOf(false) }
    var showPrivacyPolicy by remember { mutableStateOf(false) }

    val bgGradient = if (isDarkTheme) {
        Brush.verticalGradient(listOf(Color(0xFF0F172A), Color(0xFF1E293B)))
    } else {
        Brush.verticalGradient(listOf(Color(0xFFF8FAFC), Color(0xFFE2E8F0)))
    }

    val cardBg = if (isDarkTheme) Color(0xFF1E2638) else Color(0xFFFFFFFF)
    val textColor = if (isDarkTheme) Color.White else Color(0xFF0F172A)
    val subTextColor = if (isDarkTheme) Color.Gray else Color(0xFF64748B)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(bgGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Bar: Stars Badge + Settings Action Icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Total Stars Badge
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = cardBg
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Total Stars",
                            tint = Color(0xFFFFCC00),
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "$totalStars Stars",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = textColor
                        )
                    }
                }

                // Action Buttons Row (Theme, How to Play, Privacy)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Theme Toggle
                    IconButton(
                        onClick = onToggleTheme,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(cardBg)
                    ) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.WbSunny else Icons.Default.DarkMode,
                            contentDescription = "Toggle Theme",
                            tint = if (isDarkTheme) Color(0xFFFFCC00) else Color(0xFF0F172A)
                        )
                    }

                    // How to Play
                    IconButton(
                        onClick = { showHowToPlay = true },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(cardBg)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                            contentDescription = "How to Play",
                            tint = textColor
                        )
                    }

                    // Privacy Policy
                    IconButton(
                        onClick = { showPrivacyPolicy = true },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(cardBg)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = "Privacy Policy",
                            tint = textColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Game Logo & Title
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DotColor.entries.take(5).forEach { dotColor ->
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(dotColor.color)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "COLOR LINK",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp
                    ),
                    color = textColor
                )

                Text(
                    text = "Connect matching colors without crossing lines!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = subTextColor
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Difficulty Cards Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "SELECT DIFFICULTY",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = subTextColor,
                    modifier = Modifier.padding(start = 4.dp)
                )

                Difficulty.entries.forEach { difficulty ->
                    DifficultyCard(
                        difficulty = difficulty,
                        isDarkTheme = isDarkTheme,
                        onClick = { onStartGame(difficulty) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Footer Quick Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = { showHowToPlay = true },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "How to Play")
                }

                OutlinedButton(
                    onClick = { showPrivacyPolicy = true },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Privacy")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Dialogs
        if (showHowToPlay) {
            HowToPlayDialog(
                isDarkTheme = isDarkTheme,
                onDismiss = { showHowToPlay = false }
            )
        }

        if (showPrivacyPolicy) {
            PrivacyPolicyDialog(
                isDarkTheme = isDarkTheme,
                onDismiss = { showPrivacyPolicy = false }
            )
        }
    }
}

@Composable
private fun DifficultyCard(
    difficulty: Difficulty,
    isDarkTheme: Boolean,
    onClick: () -> Unit
) {
    val accentColor = when (difficulty) {
        Difficulty.EASY -> Color(0xFF34C759)
        Difficulty.MEDIUM -> Color(0xFFFF9500)
        Difficulty.HARD -> Color(0xFFFF3B30)
        Difficulty.EXPERT -> Color(0xFFAF52DE)
    }

    val cardBg = if (isDarkTheme) Color(0xFF1E2638) else Color(0xFFFFFFFF)
    val textColor = if (isDarkTheme) Color.White else Color(0xFF0F172A)
    val subTextColor = if (isDarkTheme) Color.Gray else Color(0xFF64748B)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
        color = cardBg,
        shadowElevation = if (isDarkTheme) 0.dp else 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = difficulty.title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = textColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = difficulty.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = subTextColor
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(accentColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun HowToPlayDialog(
    isDarkTheme: Boolean,
    onDismiss: () -> Unit
) {
    val cardBg = if (isDarkTheme) Color(0xFF1E2638) else Color(0xFFFFFFFF)
    val textColor = if (isDarkTheme) Color.White else Color(0xFF0F172A)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = cardBg,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "HOW TO PLAY",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = textColor
                )

                Spacer(modifier = Modifier.height(20.dp))

                HowToPlayStep(
                    stepNumber = "1",
                    title = "Connect Matching Dots",
                    description = "Drag your finger to draw a pipe between endpoints of the same color."
                )

                Spacer(modifier = Modifier.height(14.dp))

                HowToPlayStep(
                    stepNumber = "2",
                    title = "No Crossing Lines",
                    description = "Pipes cannot cross, overlap, or cut through each other."
                )

                Spacer(modifier = Modifier.height(14.dp))

                HowToPlayStep(
                    stepNumber = "3",
                    title = "Fill 100% Board",
                    description = "Cover every cell on the grid to earn a 3-star victory!"
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF007AFF),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "GOT IT!", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                }
            }
        }
    }
}

@Composable
private fun HowToPlayStep(
    stepNumber: String,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color(0xFF007AFF)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNumber,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun PrivacyPolicyDialog(
    isDarkTheme: Boolean,
    onDismiss: () -> Unit
) {
    val cardBg = if (isDarkTheme) Color(0xFF1E2638) else Color(0xFFFFFFFF)
    val textColor = if (isDarkTheme) Color.White else Color(0xFF0F172A)
    val subTextColor = if (isDarkTheme) Color.LightGray else Color(0xFF475569)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = cardBg,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "PRIVACY POLICY",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = textColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isDarkTheme) Color(0xFF141926) else Color(0xFFF1F5F9),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Color Link respects your privacy:\n\n" +
                               "• No personal data is collected, stored, or transmitted.\n\n" +
                               "• All game progress and star scores are saved 100% locally on your device.\n\n" +
                               "• No background tracking or analytics tools are used.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = subTextColor,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF007AFF),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "CLOSE", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                }
            }
        }
    }
}
