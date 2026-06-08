package com.example.eventhostmodule.ui.screens.host

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eventhostmodule.data.model.EventViewModel
import com.example.eventhostmodule.navigation.Screen
import com.example.eventhostmodule.ui.components.EventProgressBar
import androidx.compose.runtime.collectAsState
import com.example.eventhostmodule.navigation.Routes

@Composable
fun EventStep1Screen(
    navController: NavController,
    viewModel: EventViewModel
) {

    val context = LocalContext.current

    // ✅ IMPORTANT: Restore data from ViewModel
    val eventData by viewModel.eventData.collectAsState()

    var eventName by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf("Wedding") }

// 🔥 Sync with ViewModel
    LaunchedEffect(eventData) {
        eventName = eventData.eventName
        selected = if (eventData.occasion.isNotEmpty())
            eventData.occasion
        else "Wedding"
    }
    val options = listOf(
        "Wedding", "Birthday", "Corporate",
        "House Party", "Concert", "Other"
    )

    val orange = Color(0xFFF47B20)

    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    if (eventName.isEmpty()) {
                        Toast.makeText(context, "Enter event name", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.updateEventName(eventName)
                        viewModel.updateOccasion(selected)

                        navController.navigate(Routes.EVENT_STEP2)  // ✅ was Screen.EventStep3 — wrong!
                    }                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = orange)
            ) {
                Text(
                    "Continue",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {

            Spacer(Modifier.height(12.dp))

            // 🔙 HEADER
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = orange)
                }

                Text(
                    "Step 1 of 5",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(Modifier.height(16.dp))

            // 🔥 PROGRESS BAR (MATCH HOST HOME STYLE)
            EventProgressBar(
                progress = 0.2f,
                label = "20% completed",
                value = "20%"
            )

            Spacer(Modifier.height(26.dp))

            Text(
                "Tell us about your event",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(22.dp))

            Text("Event name", fontWeight = FontWeight.Medium)

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = eventName,
                onValueChange = { eventName = it },
                placeholder = { Text("Enter the name of your event") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = orange,
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                singleLine = true
            )

            Spacer(Modifier.height(26.dp))

            Text(
                "What's the occasion?",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(Modifier.height(12.dp))

            options.forEach { item ->

                val isSelected = selected == item

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .border(
                            1.5.dp,
                            if (isSelected) orange else Color(0xFFE0E0E0),
                            RoundedCornerShape(16.dp)
                        )
                        .background(
                            if (isSelected) Color(0xFFFFF3E8) else Color.White,
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { selected = item }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        item,
                        modifier = Modifier.weight(1f),
                        fontSize = 15.sp
                    )

                    RadioButton(
                        selected = isSelected,
                        onClick = { selected = item },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = orange
                        )
                    )
                }
            }

            Spacer(Modifier.height(100.dp)) // space for button
        }
    }
}

