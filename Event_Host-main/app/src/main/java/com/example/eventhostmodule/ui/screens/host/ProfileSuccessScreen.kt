package com.example.eventhostmodule.ui.screens.host
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Your project theme imports
import com.example.eventhostmodule.navigation.Screen
import com.example.eventhostmodule.ui.theme.HomeBackgroundLight
import com.example.eventhostmodule.ui.theme.HomeTextPrimary
import com.example.eventhostmodule.ui.theme.HomeTextSecondary
import com.example.eventhostmodule.ui.theme.PrimaryOrange

// For safe bottom spacing
import androidx.compose.foundation.layout.navigationBarsPadding
@Composable
fun ProfileSuccessScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackgroundLight)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        // 🎉 ICON
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color(0xFFFFE6CC), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("✔", fontSize = 40.sp, color = PrimaryOrange)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // TITLE
        Text(
            "Basic information\ncompleted 🎉",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = HomeTextPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            "Your profile is now 75% complete",
            color = HomeTextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 🔥 STATUS CARD
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F4F4))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("PROFILE STATUS", color = HomeTextSecondary)

                    Text(
                        "KYC PENDING",
                        color = PrimaryOrange,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(
                                Color(0xFFFFE6CC),
                                RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                ) {

                    val progress = 0.75f
                    val barWidth = maxWidth * progress

                    // 🔹 Background Track
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(50))
                            .background(Color(0xFFE0E0E0))
                    )

                    // 🔹 Progress Fill
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(barWidth)
                            .clip(RoundedCornerShape(50))
                            .background(PrimaryOrange)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Step 1: Details", fontSize = 12.sp)
                    Text("Step 2: KYC", fontSize = 12.sp)
                }
            }
        }

        // 🔥 KEY FIX: Push only little space, not full stretch
        Spacer(modifier = Modifier.height(60.dp))

        // 🔥 BUTTONS (NOW PERFECT POSITION)
        Button(
            onClick = { navController.navigate(Screen.KycAadhaar.route)  {
                popUpTo(Screen.HostHome.route) { inclusive = true }
            } },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
        ) {
            Text("Complete your KYC", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Go to Home",
            color = PrimaryOrange,
            modifier = Modifier.clickable {
                navController.navigate(Screen.HostHome.route + "?progress=0.75") {
                    popUpTo(Screen.HostHome.route) { inclusive = true }
                }
            }
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            "You can complete KYC anytime from your profile.",
            fontSize = 12.sp,
            color = HomeTextSecondary,
            textAlign = TextAlign.Center
        )
    }}