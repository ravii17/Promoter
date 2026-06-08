package com.example.eventhostmodule.ui.screens.host


import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eventhostmodule.navigation.Screen
import com.example.eventhostmodule.ui.components.StatusItem
import com.example.eventhostmodule.ui.theme.PrimaryOrange

@Composable
fun MatchingStatusScreen(navController: NavController) {

    val infiniteTransition = rememberInfiniteTransition()

    // 🔄 rotating animation
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(4000, easing = LinearEasing)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF7F2))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(10.dp))

        // 🔙 BACK
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, null)
            }

            Spacer(Modifier.weight(1f))

            Text(
                "Matching Status",
                fontSize = 18.sp,
                color = Color.Black
            )

            Spacer(Modifier.weight(1f))
        }

        Spacer(Modifier.height(40.dp))

        // 🔥 CIRCLE ANIMATION
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(250.dp)
        ) {

            repeat(3) { i ->
                Box(
                    modifier = Modifier
                        .size((250 - i * 40).dp)
                        .background(
                            PrimaryOrange.copy(alpha = 0.1f),
                            CircleShape
                        )
                )
            }

            // center circle
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .shadow(8.dp, CircleShape)
                    .background(PrimaryOrange, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(Modifier.height(40.dp))

        Text(
            "Finding the best event\ncompanies for you...",
            fontSize = 24.sp,
            lineHeight = 30.sp
        )

        Spacer(Modifier.height(12.dp))

        Text(
            "We’re matching your requirements with the best vendors in your area.",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(Modifier.height(10.dp))

        Text(
            "You’ll receive your recommendations within 1 hour.",
            fontSize = 14.sp,
            color = PrimaryOrange.copy(alpha = 0.7f)
        )

        Spacer(Modifier.height(30.dp))

        // 🔥 STATUS CARD
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                StatusItem("Analyzing your event details", true)
                StatusItem("Notifying event companies", false)
                StatusItem("Collecting proposals", false, isDisabled = true)
            }
        }

        Spacer(Modifier.weight(1f))

        // 🔥 BUTTON
        Button(
            onClick = {
                navController.navigate(Screen.MatchDetail.route)            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
        ) {
            Text("View Request Status", color = Color.White)
        }
    }
}