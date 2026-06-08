package com.example.eventhostmodule.ui.screens.onboarding

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.eventhostmodule.ui.components.OnboardingNextButton
import com.example.eventhostmodule.ui.components.ModernProgressDots
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
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: String,
    val subtitle: String,
    val imageUrl: String? = null,
    val showIcons: Boolean = false
)

@Composable
fun OnboardingStepsScreen(
    onNavigateToAuth: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0) { 3 }
    val coroutineScope = rememberCoroutineScope()
    val currentPage = pagerState.currentPage
    
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
    
    // Page content
    val pages = listOf(
        OnboardingPage(
            title = "Smart attendance &\nguaranteed payments",
            subtitle = "Attendance is tracked automatically, and payments are safe & guaranteed."
        ),
        OnboardingPage(
            title = "Hire verified event crew instantly",
            subtitle = "Find trusted, verified crew members for your events in seconds.",
            showIcons = true
        ),
        OnboardingPage(
            title = "Transparent, reliable,\nstress-free events",
            subtitle = "No confusion, no delays — everything is clear for organizers and crew.",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAgSh9tOh5czzPPWkS023qk5NKGnTrQIUT6oT0CqbpGecAQd61hvorSZyfDg1hZlAdJGl-Lt_eF6zHlQwaGDA9cjR1gNqqFfQma01ki443RkD2fxC2IjcGKS4OQSTtTJSnmMoaqyGg-uUKKpougzwi44SyljpsxHXhSx4A7nCmX5JriFFXrDKWtjsq9R3YKAfFbEUIGY3YQDS_KBPv-12Zax1-K-L1_8El-EqZZYYvklaQthxk5tMqrjhZVPYgydufXpMmq4u7yrZYa"
        )
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
        // Blob effects (background)
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
                .clickable(onClick = onNavigateToAuth)
        )
        
        // Horizontal Pager with smooth animations
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            pageSpacing = 0.dp
        ) { page ->
            // Illustration area
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 300.dp)
                    .zIndex(10f),
                contentAlignment = Alignment.Center
            ) {
                when (page) {
                    0 -> {
                        // Placeholder for page 1
                        Box(
                            modifier = Modifier
                                .size(300.dp)
                                .offset(y = floatOffset.dp)
                        )
                    }
                    1 -> {
                        // Icons for page 2
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(400.dp)
                                .offset(y = floatOffset.dp)
                        ) {
                            // Verified icons floating
                            Box(
                                modifier = Modifier
                                    .offset(x = 80.dp, y = 40.dp)
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
                                androidx.compose.foundation.layout.Row(
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
                    }
                    2 -> {
                        // Image for page 3
                        pages[page].imageUrl?.let { url ->
                            AsyncImage(
                                model = url,
                                contentDescription = "Event organizer and crew members",
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(400.dp)
                                    .offset(y = floatOffset.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }
            }
        }
        
        // Bottom card (fixed, shows current page content)
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
            // Modern Progress Dots
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                ModernProgressDots(
                    currentPage = currentPage,
                    totalPages = 3
                )
            }
            
            // Title (animated based on page)
            val pageContent = pages[currentPage]
            Text(
                text = pageContent.title,
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
                text = pageContent.subtitle,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary,
                lineHeight = 22.5.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )
            
            // Next/Get Started button
            OnboardingNextButton(
                text = if (currentPage == 2) "Get Started" else "Next",
                onClick = {
                    if (currentPage < 2) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(currentPage + 1)
                        }
                    } else {
                        onNavigateToAuth()
                    }
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}
