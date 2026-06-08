package com.example.eventhostmodule.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventhostmodule.ui.screens.host.Story

@Composable
fun StoryCard(story: Story) {
    Card(
        shape = RoundedCornerShape(16.dp), modifier = Modifier.width(220.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = story.image),
                contentDescription = null,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(story.type, fontSize = 10.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text(story.title, fontWeight = FontWeight.Bold)
                Text(story.subtitle, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}