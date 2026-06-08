@file:JvmName("OnboardingScreen2Kt")

package com.example.eventhostmodule.ui.screens.onboarding

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.eventhostmodule.ui.components.OnboardingNextButton
import com.example.eventhostmodule.ui.components.OnboardingProgressDots
import com.example.eventhostmodule.ui.theme.BackgroundGradientEnd
import com.example.eventhostmodule.ui.theme.BackgroundGradientMid
import com.example.eventhostmodule.ui.theme.BackgroundLight
import com.example.eventhostmodule.ui.theme.BlobColor1
import com.example.eventhostmodule.ui.theme.BlobColor2
import com.example.eventhostmodule.ui.theme.CharacterGlowStart
import com.example.eventhostmodule.ui.theme.TextPrimary
import com.example.eventhostmodule.ui.theme.TextSecondary
import com.example.eventhostmodule.ui.theme.TextTertiary
import com.example.eventhostmodule.ui.theme.White

@Composable
fun OnboardingScreen2(
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    // Floating animation
    val infiniteTransition = rememberInfiniteTransition(label = "float")
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -10f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BackgroundLight, BackgroundGradientMid, BackgroundGradientEnd)
                )
            )
    ) {
        // Blob effects
        Box(
            modifier = Modifier
                .size(384.dp)
                .offset(x = (-80).dp, y = (-40).dp)
                .background(
                    color = BlobColor1.copy(alpha = 0.4f),
                    shape = CircleShape
                )
                .blur(60.dp)
                .zIndex(0f)
        )

        Box(
            modifier = Modifier
                .size(320.dp)
                .offset(x = 120.dp, y = 80.dp)
                .background(
                    color = BlobColor2.copy(alpha = 0.3f),
                    shape = CircleShape
                )
                .blur(60.dp)
                .zIndex(0f)
        )

        // Character glow
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(300.dp)
                .offset(x = 40.dp, y = 80.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(CharacterGlowStart.copy(alpha = 0.25f), Color.Transparent),
                        center = Offset(0.5f, 0.5f),
                        radius = 500f
                    )
                )
                .zIndex(0f)
        )

        // Skip button
        Text(
            text = "Skip",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextTertiary,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp, 48.dp, 24.dp, 0.dp)
                .zIndex(20f)
                .clickable(onClick = onSkip)
        )

        // Illustration area (placeholder for SVG - will be replaced with actual illustration)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 300.dp)
                .zIndex(10f),
            contentAlignment = Alignment.Center
        ) {
            // SVG illustration would go here
            // For now, using a placeholder
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .offset(y = floatOffset.dp)
            ) {
                AsyncImage(
                    model = "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                            contentDescription ="Smart attendance illustration",
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(400.dp)
                        .offset(y = floatOffset.dp),
                    contentScale = ContentScale.Fit
                )            }
        }

        // Bottom card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    color = White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .padding(horizontal = 32.dp, vertical = 40.dp)
                .zIndex(20f)
        ) {
            // Progress dots
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                OnboardingProgressDots(
                    currentStep = 1,
                    totalSteps = 3
                )
            }

            // Title
            Text(
                text = "Smart attendance &\nguaranteed payments",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary,
                lineHeight = 33.6.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Subtitle
            Text(
                text = "Attendance is tracked automatically, and payments are safe & guaranteed.",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary,
                lineHeight = 22.5.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )

            // Next button
            OnboardingNextButton(
                text = "Next",
                onClick = onNext,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}


