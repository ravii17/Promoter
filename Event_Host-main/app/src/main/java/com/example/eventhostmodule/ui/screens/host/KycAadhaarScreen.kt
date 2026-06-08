package com.example.eventhostmodule.ui.screens.host

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eventhostmodule.navigation.Screen
import com.example.eventhostmodule.ui.theme.*
import android.provider.MediaStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KycAadhaarScreen(navController: NavController) {

    val context = LocalContext.current

    var frontImage by remember { mutableStateOf<Bitmap?>(null) }
    var backImage by remember { mutableStateOf<Bitmap?>(null) }
    var isFront by remember { mutableStateOf(true) }

    // Controls which card's bottom sheet is open: null = none, "front" or "back"
    var showSheetFor by remember { mutableStateOf<String?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            if (isFront) frontImage = bitmap else backImage = bitmap
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            if (isFront) frontImage = bitmap else backImage = bitmap
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) cameraLauncher.launch(null)
        else Toast.makeText(context, "Camera permission required", Toast.LENGTH_SHORT).show()
    }

    val isValid = frontImage != null && backImage != null

    // Bottom Sheet
    if (showSheetFor != null) {
        ModalBottomSheet(
            onDismissRequest = { showSheetFor = null },
            sheetState = sheetState,
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
                    .navigationBarsPadding()
            ) {
                Text(
                    text = "Upload Aadhaar",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF1A1A1A),
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Camera Option
                BottomSheetOption(
                    icon = Icons.Outlined.CameraAlt,
                    label = "Take Photo",
                    sublabel = "Use your camera",
                    onClick = {
                        showSheetFor = null
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Gallery Option
                BottomSheetOption(
                    icon = Icons.Outlined.Photo,
                    label = "Upload from Gallery",
                    sublabel = "Choose from your photos",
                    onClick = {
                        showSheetFor = null
                        galleryLauncher.launch("image/*")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Cancel
                OutlinedButton(
                    onClick = { showSheetFor = null },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF555555)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDDDDDD))
                ) {
                    Text("Cancel", fontWeight = FontWeight.Medium, fontSize = 15.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F0))
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        // Scrollable content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {

            // ── Header ──
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = PrimaryOrange
                    )
                }
                Text(
                    text = "Promotr",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF1A1A1A)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Title ──
            Text(
                text = "KYC",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Step 1 – Aadhaar Upload",
                color = Color(0xFF9E9E9E),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Progress ──
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "KYC is 25% complete",
                    fontSize = 13.sp,
                    color = Color(0xFF555555)
                )
                Text(
                    text = "25%",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryOrange
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFE0E0E0))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(50))
                        .background(PrimaryOrange)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── Front Card ──
            Text(
                text = "Aadhaar Upload – Front",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF1A1A1A)
            )

            Spacer(modifier = Modifier.height(10.dp))

            AadhaarUploadCard(
                image = frontImage,
                icon = Icons.Outlined.CameraAlt,
                showPlusBadge = true,
                emptyLabel = "Tap to upload front side",
                onTap = {
                    isFront = true
                    showSheetFor = "front"
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Back Card ──
            Text(
                text = "Aadhaar Upload – Back",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF1A1A1A)
            )

            Spacer(modifier = Modifier.height(10.dp))

            AadhaarUploadCard(
                image = backImage,
                icon = Icons.Outlined.CloudUpload,
                showPlusBadge = false,
                emptyLabel = "Tap to upload back side",
                onTap = {
                    isFront = false
                    showSheetFor = "back"
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        // ── Next Button pinned at bottom ──
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F0))
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Button(
                onClick = { navController.navigate(Screen.KycPan.route) },
                enabled = isValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryOrange,
                    disabledContainerColor = Color(0xFFD3D3D3)
                )
            ) {
                Text(
                    text = "Next →",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun BottomSheetOption(
    icon: ImageVector,
    label: String,
    sublabel: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFFF8F8F8))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFF0E0)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PrimaryOrange,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = label,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color(0xFF1A1A1A)
            )
            Text(
                text = sublabel,
                fontSize = 12.sp,
                color = Color(0xFF9E9E9E)
            )
        }
    }
}

@Composable
fun AadhaarUploadCard(
    image: Bitmap?,
    icon: ImageVector,
    showPlusBadge: Boolean,
    emptyLabel: String,
    onTap: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(
                width = 1.5.dp,
                color = Color(0xFFCCCCCC),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onTap() },
        contentAlignment = Alignment.Center
    ) {
        if (image != null) {
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
            )
            // Re-upload hint overlay
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xCC000000))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Tap to change",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFF0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = PrimaryOrange,
                        modifier = Modifier.size(28.dp)
                    )
                    if (showPlusBadge) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(PrimaryOrange)
                                .align(Alignment.TopEnd)
                                .offset(x = 2.dp, y = (-2).dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = emptyLabel,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = Color(0xFF1A1A1A)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Supports JPG, PNG (Max 5MB)",
                    fontSize = 12.sp,
                    color = Color(0xFF9E9E9E),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}