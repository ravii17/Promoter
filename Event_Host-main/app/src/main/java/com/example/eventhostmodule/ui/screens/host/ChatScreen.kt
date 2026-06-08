package com.example.eventhostmodule.ui.screens.host

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.eventhostmodule.ui.components.BottomBar
import com.example.eventhostmodule.ui.theme.*

@Composable
fun ChatScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(HomeBackgroundLight)
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                "Chats",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = HomeTextPrimary
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("💬", fontSize = 60.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "No conversations yet",
                    fontWeight = FontWeight.SemiBold,
                    color = HomeTextPrimary
                )
                Text(
                    "Start chatting with vendors once you book them.",
                    fontSize = 13.sp,
                    color = HomeTextSecondary
                )
            }
        }
    }
}