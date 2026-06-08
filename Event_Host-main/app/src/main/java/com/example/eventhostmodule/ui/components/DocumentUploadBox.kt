package com.example.eventhostmodule.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Text
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import coil.compose.rememberAsyncImagePainter

import com.example.eventhostmodule.ui.theme.PrimaryOrange

@Composable
fun DocumentUploadBox(
    title: String,
    onImageSelected: (Uri) -> Unit
) {
    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // 📸 Camera
    fun bitmapToUri(context: Context, bitmap: Bitmap): Uri {
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            bitmap,
            "Captured Image",
            null
        )
        return Uri.parse(path)
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            val uri = bitmapToUri(context, it)
            imageUri = uri
            onImageSelected(uri)
        }
    }

    // 🖼 Gallery
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            imageUri = it
            onImageSelected(it)
        }
    }

    Column {

        Text(title, fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .border(
                    2.dp,
                    Color(0xFFDDDDDD),
                    RoundedCornerShape(16.dp)
                )
                .clickable {
                    // 👉 Choose source (simple version: open gallery)
                    galleryLauncher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {

            if (imageUri == null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📷", fontSize = 28.sp)
                    Text("Tap to upload")
                    Text("Supports JPG, PNG", fontSize = 12.sp)
                }
            } else {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Camera",
                color = PrimaryOrange,
                modifier = Modifier.clickable {
                    cameraLauncher.launch(null)
                }
            )

            Text(
                "Gallery",
                color = PrimaryOrange,
                modifier = Modifier.clickable {
                    galleryLauncher.launch("image/*")
                }
            )
        }
    }
}