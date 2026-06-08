package com.example.eventhostmodule.ui.screens.host

import android.app.DatePickerDialog
import java.util.Calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.clip

import androidx.navigation.NavController

import com.example.eventhostmodule.ui.theme.*
import com.example.eventhostmodule.data.model.EventViewModel
import com.example.eventhostmodule.navigation.Screen
import com.example.eventhostmodule.ui.components.EventProgressBar

import androidx.compose.runtime.collectAsState
import com.example.eventhostmodule.navigation.Routes

@Composable
fun EventStep2Screen(
    navController: NavController,
    viewModel: EventViewModel
) {

    val context = LocalContext.current

    // ✅ GET DATA FROM VIEWMODEL
    val eventData by viewModel.eventData.collectAsState()

    var selectedDate by remember {
        mutableStateOf(eventData.date.ifEmpty { "Select date" })
    }
    var location by remember {
        mutableStateOf(eventData.location)
    }
    var venueType by remember {
        mutableStateOf(eventData.venueType.ifEmpty { "Indoor" })
    }
    var guests by remember {
        mutableStateOf(
            if (eventData.guests == 0) 50f else eventData.guests.toFloat()
        )
    }

    // ✅ DATE PICKER
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            selectedDate = "${
                arrayOf(
                    "Jan","Feb","Mar","Apr","May","Jun",
                    "Jul","Aug","Sep","Oct","Nov","Dec"
                )[month]
            } $day, $year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    // ✅ SAVE DATA TO VIEWMODEL
                    viewModel.updateStep2Data(
                        selectedDate,
                        location,
                        venueType,
                        guests.toInt()
                    )

                    navController.navigate(Routes.EVENT_STEP3)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(12.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
            ) {
                Text(
                    "Continue",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, null, tint = Color.White)
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
                .padding(horizontal = 20.dp)
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {

            Spacer(Modifier.height(12.dp))

            // 🔙 HEADER
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(Icons.Default.ArrowBack, null, tint = Color.Black)
                }
                Text("Step 2 of 5", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(Modifier.height(14.dp))

            // 🔥 PROGRESS BAR (FIXED)
            EventProgressBar(
                progress = 0.4f,
                label = "40% completed",
                value = "40%"
            )

            Spacer(Modifier.height(28.dp))

            Text(
                "Tell us more about the event",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(24.dp))

            // 📅 DATE
            Text("Event date", fontSize = 14.sp, color = Color(0xFF666666))
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = selectedDate,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                trailingIcon = {
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(Icons.Default.DateRange, null)
                    }
                }
            )

            Spacer(Modifier.height(20.dp))

            // 📍 LOCATION
            Text("City / location", fontSize = 14.sp, color = Color(0xFF666666))
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(Modifier.height(20.dp))

            // 🏠 VENUE TYPE
            Text("Venue Type", fontSize = 14.sp, color = Color(0xFF666666))
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF0F0F0), RoundedCornerShape(30.dp))
                    .padding(4.dp)
            ) {
                listOf("Indoor", "Outdoor").forEach { type ->
                    val isSelected = venueType == type

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                if (isSelected) PrimaryOrange else Color.Transparent,
                                RoundedCornerShape(26.dp)
                            )
                            .clickable { venueType = type }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            type,
                            color = if (isSelected) Color.White else Color.Gray
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // 👥 GUESTS
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Number of guests", fontSize = 14.sp)
                Text(
                    guests.toInt().toString(),
                    fontWeight = FontWeight.Bold
                )
            }

            Slider(
                value = guests,
                onValueChange = { guests = it },
                valueRange = 10f..100f
            )

            Spacer(Modifier.weight(1f))
        }
    }
}