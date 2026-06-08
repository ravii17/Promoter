package com.example.eventhostmodule.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventhostmodule.ui.theme.HomeTextSecondary

@Composable
fun UploadBox(
    title: String,
    image: Bitmap?,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {

    Column {

        // 🔹 TITLE
        Text(title, fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(10.dp))

        // 🔹 BOX
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .border(2.dp, Color(0xFFCCCCCC), RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {

            if (image != null) {
                Image(
                    bitmap = image.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📷", fontSize = 28.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Tap to upload")
                    Text(
                        "Supports JPG, PNG (Max 5MB)",
                        fontSize = 12.sp,
                        color = HomeTextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔥 ACTIONS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                "Camera",
                color = Color(0xFFFF7A00),
                modifier = Modifier.clickable { onCameraClick() }
            )

            Text(
                "Gallery",
                color = Color(0xFFFF7A00),
                modifier = Modifier.clickable { onGalleryClick() }
            )
        }
    }
}


@Composable
fun UploadCard(title: String, subtitle: String) {

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(title, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .border(
                        2.dp,
                        Color(0xFFDDDDDD),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📷")
                    Text(subtitle)
                    Text("Supports JPG, PNG", fontSize = 12.sp)
                }
            }
        }
    }
}