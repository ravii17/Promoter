package com.example.eventhostmodule.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventhostmodule.ui.theme.HomeTextSecondary
import com.example.eventhostmodule.ui.theme.PrimaryOrange

@Composable
fun UploadCardUI(
    title: String,
    image: Bitmap?,
    onCamera: () -> Unit,
    onGallery: () -> Unit
) {

    Column {

        Text(
            title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
                .border(
                    width = 2.dp,
                    color = Color(0xFFD6D6D6),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable { onCamera() },
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

                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color(0xFFFFE6CC), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("📷", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        "Tap to upload front side",
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        "Supports JPG, PNG (Max 5MB)",
                        fontSize = 12.sp,
                        color = HomeTextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            "Upload from gallery",
            color = PrimaryOrange,
            fontSize = 13.sp,
            modifier = Modifier.clickable { onGallery() }
        )
    }
}

