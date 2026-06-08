package com.example.eventhostmodule.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.eventhostmodule.ui.theme.PrimaryOrange
import com.example.eventhostmodule.ui.theme.Slate200
import com.example.eventhostmodule.ui.theme.Slate700

@Composable
fun OnboardingProgressDots(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier,
    isDarkMode: Boolean = false
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(totalSteps) { index ->
            val isActive = index == currentStep
            val width = if (isActive) 24.dp else 6.dp
            val color = if (isActive) {
                PrimaryOrange
            } else {
                if (isDarkMode) Slate700 else Slate200
            }
            
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .width(width)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(color)
            )
        }
    }
}
