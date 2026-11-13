package com.tariqnaeemdeveloper.colorlink.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tariqnaeemdeveloper.colorlink.R
import com.tariqnaeemdeveloper.colorlink.model.DotColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    isDarkTheme: Boolean,
    onSplashFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale = remember { Animatable(0.5f) }
    val alpha = remember { Animatable(0f) }

    // Pulsing animation for loading dots
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    LaunchedEffect(Unit) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 800)
        )
        delay(1800)
        onSplashFinished()
    }

    val bgGradient = if (isDarkTheme) {
        Brush.verticalGradient(listOf(Color(0xFF090D16), Color(0xFF131B2E), Color(0xFF1E293B)))
    } else {
        Brush.verticalGradient(listOf(Color(0xFFFFFFFF), Color(0xFFF1F5F9), Color(0xFFE2E8F0)))
    }

    val textColor = if (isDarkTheme) Color.White else Color(0xFF0F172A)
    val subTextColor = if (isDarkTheme) Color(0xFF94A3B8) else Color(0xFF64748B)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(bgGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Logo Container with Ambient Glow
            Surface(
                modifier = Modifier
                    .size(160.dp)
                    .scale(scale.value)
                    .alpha(alpha.value),
                shape = RoundedCornerShape(36.dp),
                color = if (isDarkTheme) Color(0xFF1E2638) else Color(0xFFFFFFFF),
                shadowElevation = 16.dp
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.applogo),
                        contentDescription = "Color Link Logo",
                        modifier = Modifier
                            .size(130.dp)
                            .clip(RoundedCornerShape(28.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // App Title & Tagline
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.alpha(alpha.value)
            ) {
                Text(
                    text = "COLOR LINK",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 4.sp
                    ),
                    color = textColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "A COLORFUL LOGIC PUZZLE",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    ),
                    color = subTextColor
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Pulsing Loading Dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.alpha(alpha.value)
            ) {
                DotColor.entries.take(5).forEachIndexed { index, dotColor ->
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .scale(if (index % 2 == 0) pulseScale else 2f - pulseScale)
                            .clip(CircleShape)
                            .background(dotColor.color)
                    )
                }
            }
        }
    }
}
