package com.example.eventhostmodule.ui.screens.host

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle

import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

import androidx.navigation.NavController

import com.example.eventhostmodule.ui.theme.*

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.ui.draw.clip

import com.example.eventhostmodule.data.model.EventViewModel
import com.example.eventhostmodule.navigation.Screen
import com.example.eventhostmodule.ui.components.EventProgressBar

import androidx.compose.runtime.collectAsState
import com.example.eventhostmodule.navigation.Routes

@Composable
fun EventStep3Screen(
    navController: NavController,
    viewModel: EventViewModel
) {

    val eventData by viewModel.eventData.collectAsState()

    val allServices = listOf(
        "Decoration", "Catering", "DJ / Music", "Photography",
        "Videography", "Volunteers", "Technicians", "Helpers",
        "Makeup", "Full event management"
    )

    // ✅ RESTORE FROM VIEWMODEL
    val selected = remember {
        mutableStateListOf<String>().apply {
            addAll(eventData.services)
        }
    }

    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    // ✅ SAVE DATA
                    viewModel.updateServices(selected)

                    navController.navigate(Routes.EVENT_STEP4)
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
                Text("Step 3 of 5", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(Modifier.height(14.dp))

            // 🔥 PROGRESS BAR
            EventProgressBar(
                progress = 0.6f,
                label = "60% completed",
                value = "60%"
            )

            Spacer(Modifier.height(28.dp))

            Text(
                "What do you need for this event?",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Select all that apply to your event needs.",
                fontSize = 14.sp,
                color = Color(0xFF888888)
            )

            Spacer(Modifier.height(24.dp))

            // 🔥 SERVICES GRID
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                allServices.forEach { service ->

                    val isSelected = selected.contains(service)

                    Row(
                        modifier = Modifier
                            .border(
                                1.5.dp,
                                if (isSelected) PrimaryOrange else Color(0xFFE0E0E0),
                                RoundedCornerShape(12.dp)
                            )
                            .background(
                                if (isSelected) Color(0xFFFFF3E8) else Color(0xFFF5F5F5),
                                RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                if (isSelected) selected.remove(service)
                                else selected.add(service)
                            }
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        if (isSelected) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = PrimaryOrange,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(Modifier.width(6.dp))

                        Text(
                            service,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = Color(0xFF111111)
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))
        }
    }
}