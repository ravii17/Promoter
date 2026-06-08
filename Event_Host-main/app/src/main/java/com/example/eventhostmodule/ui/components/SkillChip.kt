package com.example.eventhostmodule.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventhostmodule.ui.theme.RegistrationPrimaryExp

@Composable
fun SkillChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) RegistrationPrimaryExp.copy(alpha = 0.1f) else Color(0xFFFFFFFF),
        animationSpec = tween(200),
        label = "skill_bg"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) RegistrationPrimaryExp.copy(alpha = 0.2f) else Color(0xFFE8DBCE),
        animationSpec = tween(200),
        label = "skill_border"
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) RegistrationPrimaryExp else Color(0xFF9C7349),
        animationSpec = tween(200),
        label = "skill_text"
    )

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(),
        label = "skill_scale"
    )

    Row(
        modifier = modifier
            .scale(scale)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = textColor
        )
        Icon(
            imageVector = if (isSelected) Icons.Default.Close else Icons.Default.Add,
            contentDescription = if (isSelected) "Remove" else "Add",
            tint = textColor,
            modifier = Modifier.scale(0.9f)
        )
    }
}
