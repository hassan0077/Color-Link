package com.tariqnaeemdeveloper.colorlink.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.util.Locale

@Composable
fun VictoryDialog(
    starsEarned: Int,
    moves: Int,
    coveragePercentage: Int,
    elapsedSeconds: Int,
    hasNextLevel: Boolean,
    onNextLevelClick: () -> Unit,
    onReplayClick: () -> Unit,
    onLevelSelectClick: () -> Unit,
    onDismissRequest: () -> Unit = {}
) {
    val cardBg = MaterialTheme.colorScheme.surface
    val statsBg = MaterialTheme.colorScheme.surfaceVariant
    val textColor = MaterialTheme.colorScheme.onSurface
    val subTextColor = MaterialTheme.colorScheme.onSurfaceVariant

    Dialog(onDismissRequest = onDismissRequest) {
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
                    text = "LEVEL COMPLETE!",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = textColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Animated Star Rating Row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 1..3) {
                        val isEarned = i <= starsEarned
                        val scale = remember { Animatable(0f) }

                        LaunchedEffect(i) {
                            if (isEarned) {
                                scale.animateTo(
                                    targetValue = 1f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            } else {
                                scale.snapTo(1f)
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star $i",
                            modifier = Modifier
                                .size(if (i == 2) 48.dp else 40.dp)
                                .scale(scale.value),
                            tint = if (isEarned) Color(0xFFFFCC00) else Color(0xFF94A3B8)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Stats Summary Box
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = statsBg
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        DialogStatItem("MOVES", "$moves", textColor, subTextColor)
                        DialogStatItem("FILL", "$coveragePercentage%", textColor, subTextColor)
                        DialogStatItem("TIME", formatDialogTime(elapsedSeconds), textColor, subTextColor)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action Buttons
                if (hasNextLevel) {
                    Button(
                        onClick = onNextLevelClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF007AFF),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "NEXT LEVEL",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }

                OutlinedButton(
                    onClick = onReplayClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "REPLAY",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onLevelSelectClick) {
                    Text(
                        text = "LEVEL SELECT",
                        style = MaterialTheme.typography.bodyLarge,
                        color = subTextColor
                    )
                }
            }
        }
    }
}

@Composable
private fun DialogStatItem(title: String, value: String, textColor: Color, subTextColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = subTextColor
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = textColor
        )
    }
}

private fun formatDialogTime(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.US, "%02d:%02d", minutes, seconds)
}
