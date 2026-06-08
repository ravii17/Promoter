package com.example.eventhostmodule.ui.screens.host

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.FlowRow

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale

import androidx.navigation.NavController
import coil.compose.AsyncImage

import com.example.eventhostmodule.data.model.EventViewModel
import com.example.eventhostmodule.navigation.Screen
import com.example.eventhostmodule.ui.components.ServiceItem
import com.example.eventhostmodule.ui.theme.PrimaryOrange

@Composable
fun ConfirmBookingScreen(
    navController: NavController,
    viewModel: EventViewModel
) {

    val data by viewModel.eventData.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF7F2))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {

        // 🔙 HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, null)
            }

            Text(
                "Confirm your booking",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            // 🔥 VENDOR CARD
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {

                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1523438885200-e635ba2c371e",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )

                    Column(Modifier.padding(16.dp)) {

                        Text(
                            "VENDOR SUMMARY",
                            color = PrimaryOrange,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(6.dp))

                        // ✅ Keep vendor static
                        Text("Grand Events Co.", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                        Spacer(Modifier.height(6.dp))

                        // ✅ YOUR EVENT NAME
                        Text(
                            if (data.eventName.isNotEmpty()) data.eventName else "Event Name",
                            color = Color.Gray
                        )

                        Spacer(Modifier.height(6.dp))

                        // ✅ DATE + LOCATION
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.DateRange, null, modifier = Modifier.size(16.dp))
                            Text(
                                " ${if (data.date.isNotEmpty()) data.date else "Date not selected"} • ${
                                    if (data.location.isNotEmpty()) data.location else "Location"
                                }",
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // 🔥 SELECTED SERVICES
            Text("Selected Services", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(12.dp))

            if (data.services.isEmpty()) {
                Text("No services selected", color = Color.Gray)
            } else {
                data.services.forEach { service ->
                    ServiceItem(service, "Included")
                }
            }

            Spacer(Modifier.height(20.dp))

            // 🔥 PRICE BOX (STATIC AS YOU WANTED)
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(Modifier.padding(16.dp)) {

                    Row {
                        Text("Base Amount", modifier = Modifier.weight(1f))
                        Text("₹42,372")
                    }

                    Spacer(Modifier.height(6.dp))

                    Row {
                        Text("GST (18%)", modifier = Modifier.weight(1f))
                        Text("₹7,628")
                    }

                    Divider(modifier = Modifier.padding(vertical = 10.dp))

                    Row {
                        Text("Total Payable", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.weight(1f))
                        Text(
                            "₹50,000",
                            color = PrimaryOrange,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // 🔥 PAY BUTTON
        Column(modifier = Modifier.padding(16.dp)) {

            Button(
                onClick = {
                    Toast.makeText(
                        navController.context,
                        "Payment Successful 🎉",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.markEventCreated()
                    navController.navigate(Screen.BookingSuccess.route)

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
            ) {
                Text("Pay ₹50,000 🔒", color = Color.White)
            }

            Spacer(Modifier.height(10.dp))

            Text(
                "View cancellation policy",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.Gray
            )
        }
    }
}
@Composable
fun SectionHeader(title: String, onEdit: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(
            "Edit",
            color = PrimaryOrange,
            modifier = Modifier.clickable { onEdit() }
        )
    }
}