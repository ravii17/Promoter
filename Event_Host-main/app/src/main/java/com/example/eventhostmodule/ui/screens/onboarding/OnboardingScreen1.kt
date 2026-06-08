package com.example.eventhostmodule.ui.screens.onboarding

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.eventhostmodule.ui.components.OnboardingNextButton
import com.example.eventhostmodule.ui.components.OnboardingProgressDots
import com.example.eventhostmodule.ui.theme.BackgroundGradientEnd
import com.example.eventhostmodule.ui.theme.BackgroundGradientMid
import com.example.eventhostmodule.ui.theme.BackgroundLight
import com.example.eventhostmodule.ui.theme.BlobColor1
import com.example.eventhostmodule.ui.theme.BlobColor2
import com.example.eventhostmodule.ui.theme.CharacterGlowStart
import com.example.eventhostmodule.ui.theme.PrimaryOrange
import com.example.eventhostmodule.ui.theme.TextPrimary
import com.example.eventhostmodule.ui.theme.TextSecondary
import com.example.eventhostmodule.ui.theme.TextTertiary
import com.example.eventhostmodule.ui.theme.White


@Composable
fun OnboardingScreen1(
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    // Floating animation
    val infiniteTransition = rememberInfiniteTransition(label = "float")
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -10f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BackgroundLight, BackgroundGradientMid, BackgroundGradientEnd)
                )
            )
    ) {
        // Blob effects
        Box(
            modifier = Modifier
                .size(384.dp)
                .offset(x = (-80).dp, y = (-40).dp)
                .background(
                    color = BlobColor1.copy(alpha = 0.4f),
                    shape = CircleShape
                )
                .blur(60.dp)
                .zIndex(0f)
        )
        
        Box(
            modifier = Modifier
                .size(320.dp)
                .offset(x = 120.dp, y = 80.dp)
                .background(
                    color = BlobColor2.copy(alpha = 0.3f),
                    shape = CircleShape
                )
                .blur(60.dp)
                .zIndex(0f)
        )
        
        // Character glow
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(300.dp)
                .offset(x = 40.dp, y = 80.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(CharacterGlowStart.copy(alpha = 0.25f), Color.Transparent),
                        center = androidx.compose.ui.geometry.Offset(0.5f, 0.5f),
                        radius = 500f
                    )
                )
                .zIndex(0f)
        )
        
        // Skip button
        Text(
            text = "Skip",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextTertiary,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp, 48.dp, 24.dp, 0.dp)
                .zIndex(20f)
                .clickable(onClick = onSkip)
        )
        
        // Illustration area with floating icons
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 300.dp)
                .zIndex(10f),
            contentAlignment = Alignment.Center
        ) {
            // Main illustration image
            AsyncImage(
                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBDosfsThRJl8zkTHgEq7I-1aV2hE-_VOjjhNMzQE0YAtElrI45EubDUumt6nkO-K7y-9kbGKb586SMjfmylLMPoQZfFymhuZQWF3V3ZLNwBYParnWzM-1K5ccNZhSx3uMeauR5drpvgLrlMBcYK8XYrJshIJWWAS-3C4U-6f1ya0x2aB4vXAPRDSJP8Xa2Eio5n_raNvpv1Af0p5NdIeyKc0vC0c-snVMediUSYS3q6xjsEHyzVar5dl9xKVFGtUVgUvFdOsEeaO3V",
                contentDescription = "Verified event crew team",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(400.dp)
                    .offset(y = floatOffset.dp),
                contentScale = ContentScale.Fit
            )
            
            // Floating verification icons
            Box(
                modifier = Modifier
                    .offset(x = (-160).dp, y = (-180).dp)
                    .size(40.dp)
                    .background(
                        color = White.copy(alpha = 0.95f),
                        shape = CircleShape
                    )
                    .offset(y = floatOffset.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Verified",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Box(
                modifier = Modifier
                    .offset(x = 160.dp, y = (-100).dp)
                    .size(40.dp)
                    .background(
                        color = White.copy(alpha = 0.95f),
                        shape = CircleShape
                    )
                    .offset(y = floatOffset.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Verified",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Box(
                modifier = Modifier
                    .offset(x = (-120).dp, y = 20.dp)
                    .size(40.dp)
                    .background(
                        color = White.copy(alpha = 0.95f),
                        shape = CircleShape
                    )
                    .offset(y = floatOffset.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Verified",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Box(
                modifier = Modifier
                    .offset(x = (-160).dp, y = 200.dp)
                    .size(56.dp)
                    .background(
                        color = White.copy(alpha = 0.95f),
                        shape = CircleShape
                    )
                    .offset(y = floatOffset.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Security",
                    tint = PrimaryOrange,
                    modifier = Modifier.size(28.dp)
                )
            }
            
            Box(
                modifier = Modifier
                    .offset(x = 120.dp, y = 240.dp)
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .background(
                        color = White.copy(alpha = 0.95f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .offset(y = floatOffset.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Badge",
                        tint = TextTertiary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ID Verified",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
            }
        }
        
        // Bottom card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    color = White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .padding(horizontal = 32.dp, vertical = 40.dp)
                .zIndex(20f)
        ) {
            // Progress dots
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                OnboardingProgressDots(
                    currentStep = 0,
                    totalSteps = 3
                )
            }
            
            // Title
            Text(
                text = "Hire verified event crew instantly",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary,
                lineHeight = 33.6.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            
            // Subtitle
            Text(
                text = "Find trusted promoters, technicians, and event staff for any event — all in one app.",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary,
                lineHeight = 22.5.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )
            
            // Next button
            OnboardingNextButton(
                text = "Next",
                onClick = onNext,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}
