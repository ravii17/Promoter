package com.example.eventhostmodule.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.eventhostmodule.ui.theme.PrimaryOrange
import com.example.eventhostmodule.ui.theme.Slate200

@Composable
fun ModernProgressDots(
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(totalPages) { index ->
            val isActive = index == currentPage
            val animatedWidth by animateDpAsState(
                targetValue = if (isActive) 24.dp else 8.dp,
                animationSpec = tween(durationMillis = 300),
                label = "dot_width"
            )
            val color = if (isActive) {
                PrimaryOrange
            } else {
                Slate200
            }
            
            Box(
                modifier = Modifier
                    .width(animatedWidth)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(color)
            )
        }
    }
}
