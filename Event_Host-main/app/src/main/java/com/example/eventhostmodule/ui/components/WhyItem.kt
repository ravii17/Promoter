package com.example.eventhostmodule.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.sp
import com.example.eventhostmodule.ui.theme.PrimaryOrange

@Composable
fun WhyItem(title: String, desc: String) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(Color(0xFFFFEAD8), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Check, null, tint = PrimaryOrange, modifier = Modifier.size(16.dp))
        }

        Spacer(Modifier.width(10.dp))

        Column {
            Text(title)
            Text(desc, fontSize = 12.sp, color = Color.Gray)
        }
    }
}