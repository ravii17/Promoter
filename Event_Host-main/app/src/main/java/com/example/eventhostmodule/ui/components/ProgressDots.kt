package com.example.eventhostmodule.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.eventhostmodule.ui.theme.BrandOrange
import com.example.eventhostmodule.ui.theme.Gray

@Composable
fun ProgressDots(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = BrandOrange,
    inactiveColor: Color = Gray.copy(alpha = 0.3f)
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(totalSteps) { index ->
            val isSelected = index == currentStep
            val scale = animateFloatAsState(
                targetValue = if (isSelected) 1.3f else 1f,
                animationSpec = tween(durationMillis = 180),
                label = "dot_scale"
            )
            
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .scale(scale.value)
                    .background(
                        color = if (isSelected) activeColor else inactiveColor,
                        shape = CircleShape
                    )
            )
        }
    }
}
