package com.example.eventhostmodule.ui.screens.host

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp

@Composable
fun CompleteProfileIntroScreen(
    onStartClick: () -> Unit,
    onClose: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F6F4))
    ) {

        // ❌ Close Button (Top Right)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = 0.3f))
                .clickable { onClose() },
            contentAlignment = Alignment.Center
        ) {
            Text("✕", fontSize = 18.sp, color = Color.Gray)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // 🔥 Illustration + floating badge
            Box(
                contentAlignment = Alignment.Center
            ) {

                // Card Illustration (placeholder image)
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White)
                        .shadow(10.dp, RoundedCornerShape(24.dp))
                )

                // 🔥 Orange floating icon
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 20.dp, y = 20.dp)
                        .size(70.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFF8A00),
                                    Color(0xFFFF6A00)
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("✔", color = Color.White, fontSize = 24.sp)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // 🔥 Title
            Text(
                text = "Complete your\nprofile",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Subtitle
            Text(
                text = "To start applying for jobs and unlock all features, you need to complete your basic information.",
                fontSize = 14.sp,
                color = Color(0xFF7A7A7A),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }

        // 🔥 Bottom Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
        ) {
            Button(
                onClick = onStartClick,
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFFF8A00),
                                    Color(0xFFFF6A00)
                                )
                            ),
                            shape = RoundedCornerShape(50)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Let's Start",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}