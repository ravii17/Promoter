package com.example.eventhostmodule.ui.screens.host

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import com.example.eventhostmodule.data.model.EventViewModel
import com.example.eventhostmodule.navigation.Screen
import com.example.eventhostmodule.ui.theme.PrimaryOrange

@Composable
fun BookingSuccessScreen(
    navController: NavController,
    viewModel: EventViewModel
) {

    val data by viewModel.eventData.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF7F2))
            .padding(16.dp)
    ) {

        // ❌ CLOSE BUTTON
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = {
                navController.navigate(Screen.HostHome.route) {
                    popUpTo(0)
                }
            }) {
                Icon(Icons.Default.Close, contentDescription = null)
            }
        }

        Spacer(Modifier.height(10.dp))

        // 🔥 TITLE
        Text(
            "Confirmation",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(20.dp))

        // 🔥 SUCCESS ICON
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(PrimaryOrange.copy(alpha = 0.1f), shape = RoundedCornerShape(100.dp))
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = PrimaryOrange,
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(Modifier.height(20.dp))

        // 🔥 TEXT
        Text(
            "Booking confirmed! 🎉",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Your payment was successful and the booking is secured.",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(20.dp))

        // 🔥 CARD
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {

                AsyncImage(
                    model = "https://images.unsplash.com/photo-1523438885200-e635ba2c371e",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.Crop
                )

                Column(Modifier.padding(16.dp)) {

                    Text("Grand Events Co.", fontWeight = FontWeight.Bold)

                    Spacer(Modifier.height(6.dp))

                    Text(data.eventName)

                    Spacer(Modifier.height(6.dp))

                    Text(data.date)

                    Spacer(Modifier.height(6.dp))

                    Text("₹${data.budget} paid", fontWeight = FontWeight.Bold)

                    Spacer(Modifier.height(10.dp))

                    Divider()

                    Spacer(Modifier.height(8.dp))

                    Row {
                        Text("BOOKING ID", color = Color.Gray)
                        Spacer(Modifier.weight(1f))
                        Text("#PRM7890", color = PrimaryOrange)
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        // 🔥 BUTTONS
        Button(
            onClick = {
                navController.navigate(Screen.HostHome.route) {
                    popUpTo(0)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
        ) {
            Text("Go to Event Dashboard", color = Color.White)
        }

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = {
                navController.navigate(Screen.Chat.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Text("Chat with support", color = Color.Black)
        }

        Spacer(Modifier.height(10.dp))

        Text(
            "🔒 Securely processed by Promotr Pay",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}