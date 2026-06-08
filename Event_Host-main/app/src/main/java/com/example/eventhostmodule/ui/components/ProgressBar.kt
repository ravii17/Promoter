package com.example.eventhostmodule.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(
    progress: Float, // 0.0 to 1.0
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFE5E7EB),
    progressColor: Color = Color(0xFFEC7F13),
    height: androidx.compose.ui.unit.Dp = 8.dp
) {
    var animatedProgress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(progress) {
        animatedProgress = progress
    }

    val animatedWidth by animateFloatAsState(
        targetValue = animatedProgress,
        animationSpec = tween(durationMillis = 500, delayMillis = 100),
        label = "progress_animation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(9999.dp)
            )
            .clip(RoundedCornerShape(9999.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedWidth)
                .height(height)
                .background(
                    color = progressColor,
                    shape = RoundedCornerShape(9999.dp)
                )
        )
    }
}
