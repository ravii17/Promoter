package com.example.organisation

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun PanUploadScreen(
    frontImageUri: Uri?,
    backImageUri: Uri?,
    onFrontUploadClick: () -> Unit,
    onBackUploadClick: () -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val orangeColor = Color(0xFFFF7A00)
    val textMainColor = Color(0xFF1A1A1A)
    val textSubColor = Color(0xFF757575)
    val lightOrangeBg = Color(0xFFFFF2E5)
    val lightBlueBg = Color(0xFFF0F7FF)
    val blueIconColor = Color(0xFF0066FF)

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = orangeColor,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.CenterStart)
                        .clickable { onBackClick() }
                )
                
                Text(
                    text = "Promoter",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = textMainColor,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .navigationBarsPadding()
                    .padding(24.dp)
            ) {
                Button(
                    onClick = onNextClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = orangeColor
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Next",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Next",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            Text(
                text = "KYC",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                color = textMainColor
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "KYC is 66% complete",
                    color = Color(0xFFB88E70),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "2/3",
                    color = orangeColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = { 0.66f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = orangeColor,
                trackColor = Color(0xFFF0F0F0)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Step 2 – PAN Upload",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = textMainColor
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Please upload clear photos of your PAN card.",
                color = textSubColor,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            UploadCard(
                title = "PAN Card Front",
                actionText = "Tap to upload front side",
                imageUri = frontImageUri,
                orangeColor = orangeColor,
                lightOrangeBg = lightOrangeBg,
                onUploadClick = onFrontUploadClick
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            UploadCard(
                title = "PAN Card Back",
                actionText = "Tap to upload back side",
                imageUri = backImageUri,
                orangeColor = orangeColor,
                lightOrangeBg = lightOrangeBg,
                onUploadClick = onBackUploadClick
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(lightBlueBg)
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    tint = blueIconColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Ensure your name and PAN number are clearly visible. Blurry images may lead to rejection.",
                    color = Color(0xFF3B688A),
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun UploadCard(
    title: String,
    actionText: String,
    imageUri: Uri?,
    orangeColor: Color,
    lightOrangeBg: Color,
    onUploadClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color(0xFF1A1A1A)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            val stroke = Stroke(width = 3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .drawBehind {
                        drawRoundRect(
                            color = Color(0xFFE0E0E0),
                            style = stroke,
                            cornerRadius = CornerRadius(16.dp.toPx())
                        )
                    }
                    .background(Color(0xFFFAFAFA), RoundedCornerShape(16.dp))
                    .clickable { onUploadClick() },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(lightOrangeBg, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.AddAPhoto,
                                contentDescription = "Camera",
                                tint = orangeColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text(
                            text = actionText,
                            color = Color(0xFFB58055),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = "Supports JPG, PNG",
                            color = Color(0xFFA0A0A0),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
