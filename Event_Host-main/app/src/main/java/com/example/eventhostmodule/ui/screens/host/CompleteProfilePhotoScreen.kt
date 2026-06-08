package com.example.eventhostmodule.ui.screens.host

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas

// Your theme imports
import com.example.eventhostmodule.ui.theme.HomeBackgroundLight
import com.example.eventhostmodule.ui.theme.PrimaryOrange

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.platform.LocalContext
import android.Manifest
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import com.example.eventhostmodule.navigation.Screen
import com.example.eventhostmodule.ui.theme.HomeTextPrimary
import com.example.eventhostmodule.ui.theme.HomeTextSecondary

@Composable
fun CompleteProfilePhotoScreen(navController: NavController) {

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // 🔥 Bitmap → Uri
    fun bitmapToUri(context: Context, bitmap: Bitmap): Uri {
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            bitmap,
            "Captured Image",
            null
        )
        return Uri.parse(path)
    }

    // 📸 Camera
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            imageUri = bitmapToUri(context, it)

        }
    }

    // 🔐 Permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) cameraLauncher.launch(null)
        else Toast.makeText(context, "Camera permission required", Toast.LENGTH_SHORT).show()
    }

    // 🖼 Gallery
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackgroundLight)
            .padding(horizontal = 20.dp)
            .navigationBarsPadding()   // ✅ FIXED
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        // 🔙 Back
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // STEP TEXT
        Text(
            "STEP 2 OF 2",
            color = PrimaryOrange,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Photo Verification", color = HomeTextSecondary)
            Text("100%", color = PrimaryOrange, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(6.dp))

        LinearProgressIndicator(
            progress = 1f,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(50)),
            color = PrimaryOrange,
            trackColor = Color(0xFFE0E0E0)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // TITLE
        Text(
            "Add your photo",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = HomeTextPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            "Take a clear selfie so event companies\nknow it’s really you",
            color = Color(0xFF8A6D4D),
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        // 🔥 MAIN CIRCLE
        Box(
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {

            // ✅ DOTTED CIRCLE (CORRECT)
            Canvas(modifier = Modifier.matchParentSize()) {
                drawCircle(
                    color = Color(0xFFD6C2A8),
                    style = Stroke(
                        width = 4f,
                        pathEffect = PathEffect.dashPathEffect(
                            floatArrayOf(12f, 12f), 0f
                        )
                    )
                )
            }

            // INNER CIRCLE
            Box(
                modifier = Modifier
                    .size(190.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                                        //Text("📷", fontSize = 40.sp)
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = "Add photo",
                        tint = PrimaryOrange.copy(alpha = 0.5f),
                        modifier = Modifier.size(54.dp)
                    )
                }
            }

            // ➕ BUTTON
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 10.dp, y = 10.dp)
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(PrimaryOrange)
                    .clickable {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // TRUST TEXT
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("🛡️", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                "This helps build trust and avoid fake profiles",
                color = Color(0xFF8A6D4D),
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

// MAIN BUTTON
        Button(
            onClick = {
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
        ) {
            Text("Take Photo", color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

// SECONDARY ACTION
        Text(
            "Upload from gallery",
            color = PrimaryOrange,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    galleryLauncher.launch("image/*")
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

// ✅ SHOW ONLY IF IMAGE SELECTED
        if (imageUri != null) {
            Button(
                onClick = {
                    navController.navigate(Screen.ProfileSuccess.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
            ) {
                Text("Done", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}