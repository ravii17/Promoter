package com.example.eventhostmodule.ui.screens.host
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController
import com.example.eventhostmodule.data.model.EventViewModel
import com.example.eventhostmodule.navigation.Routes
import com.example.eventhostmodule.navigation.Screen
import com.example.eventhostmodule.ui.components.EventProgressBar

import com.example.eventhostmodule.ui.theme.*
@Composable
fun EventStep5Screen(
    navController: NavController,
    viewModel: EventViewModel
) {

    // ✅ GET DATA FROM VIEWMODEL
    val eventData by viewModel.eventData.collectAsState()

    // ✅ RESTORE PREVIOUS VALUES
    var theme by remember {
        mutableStateOf(eventData.theme)
    }

    var notes by remember {
        mutableStateOf(eventData.notes)
    }

    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    // ✅ SAVE DATA
                    viewModel.updatePreferences(theme, notes)

                    navController.navigate(Routes.EVENT_CONFIRM)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(12.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
            ) {
                Text(
                    "Find best event companies",
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
                    modifier = Modifier.align(Alignment.CenterStart) // ✅ FIX

                ) {
                    Icon(Icons.Default.ArrowBack, null)
                }
                Text("Step 5 of 5",
                    modifier = Modifier.align(Alignment.Center), // ✅ center text
                    fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(14.dp))

            // 🔥 PROGRESS BAR
            EventProgressBar(
                progress = 1f,
                label = "100% completed",
                value = "100%"
            )

            Spacer(Modifier.height(32.dp))

            Text(
                "Any special preferences?",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(10.dp))

            Text(
                "Personalize your event experience.",
                fontSize = 14.sp,
                color = Color(0xFF666666)
            )

            Spacer(Modifier.height(28.dp))

            // 🎨 THEME
            Text("Theme", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = theme,
                onValueChange = { theme = it },
                placeholder = { Text("e.g. Minimalist, Rustic") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(Modifier.height(22.dp))

            // 📝 NOTES
            Text("Additional notes", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                placeholder = { Text("Any special requests...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(14.dp),
                maxLines = 8
            )

            Spacer(Modifier.weight(1f))
        }
    }
}