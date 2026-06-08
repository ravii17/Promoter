package com.example.eventhostmodule.ui.screens.onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventhostmodule.R
import com.example.eventhostmodule.data.local.SharedPrefManager
import com.example.eventhostmodule.ui.theme.BrandOrange
import com.example.eventhostmodule.ui.theme.BrandOrangeDark
import com.example.eventhostmodule.ui.theme.White

@Composable
fun OnboardingSelectionScreen(
    onNavigateToMain: () -> Unit
) {
    val context = LocalContext.current
    var jobsSelected by remember { mutableStateOf(false) }
    var staffSelected by remember { mutableStateOf(false) }
    
    val jobsBorderWidth by animateDpAsState(
        targetValue = if (jobsSelected) 3.dp else 0.dp,
        animationSpec = tween(durationMillis = 200),
        label = "jobs_border"
    )
    
    val staffBorderWidth by animateDpAsState(
        targetValue = if (staffSelected) 3.dp else 0.dp,
        animationSpec = tween(durationMillis = 200),
        label = "staff_border"
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            // Logo at top
            Text(
                text = "aquila.",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = BrandOrange,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 24.dp, top = 40.dp)
            )
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Question
            Text(
                text = "What are you looking for ?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Jobs Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .border(
                        width = jobsBorderWidth,
                        color = BrandOrangeDark,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable {
                        jobsSelected = !jobsSelected
                        staffSelected = false
                    },
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(BrandOrange, BrandOrangeDark)
                            )
                        )
                        .clip(RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_briefcase),
                            contentDescription = "Jobs Icon",
                            modifier = Modifier.size(36.dp),
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(White)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "For Jobs",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Staff Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .border(
                        width = staffBorderWidth,
                        color = BrandOrangeDark,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable {
                        staffSelected = !staffSelected
                        jobsSelected = false
                    },
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(BrandOrange, BrandOrangeDark)
                            )
                        )
                        .clip(RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_people),
                            contentDescription = "Staff Icon",
                            modifier = Modifier.size(36.dp),
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(White)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "For Staff",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Tagline
            Text(
                text = "Great opportunities start with the right connection.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = BrandOrange,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        // Skip button at bottom
        Text(
            text = "Skip",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = BrandOrange,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .clickable {
                    SharedPrefManager.getInstance(context).setGuest(true)
                    onNavigateToMain()
                }
        )
    }
}
