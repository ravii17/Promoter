package com.example.eventhostmodule.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.eventhostmodule.ui.theme.PrimaryOrange

@Composable
fun StatusItem(text: String, done: Boolean, isDisabled: Boolean = false) {

    val color = when {
        done -> Color(0xFF2ECC71)
        isDisabled -> Color.LightGray
        else -> PrimaryOrange
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 10.dp)
    ) {

        Box(
            modifier = Modifier
                .size(22.dp)
                .background(color, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (done) {
                Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(14.dp))
            }
        }

        Spacer(Modifier.width(12.dp))

        Text(
            text,
            color = if (isDisabled) Color.Gray else Color.Black
        )
    }
}