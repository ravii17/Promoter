package com.example.eventhostmodule.ui.screens.host

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions          // ✅ correct import
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType                // ✅ correct import
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
fun EventStep4Screen(
    navController: NavController,
    viewModel: EventViewModel
) {

    val context = LocalContext.current

    // ✅ GET DATA FROM VIEWMODEL
    val eventData by viewModel.eventData.collectAsState()

    // ✅ RESTORE PREVIOUS VALUE
    var budget by remember {
        mutableStateOf(eventData.budget)
    }

    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    if (budget.isEmpty()) {
                        Toast.makeText(context, "Enter your budget", Toast.LENGTH_SHORT).show()
                    } else {

                        // ✅ SAVE TO VIEWMODEL
                        viewModel.updateBudget(budget)

                        navController.navigate(Routes.EVENT_STEP5)
                    }
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
                    modifier = Modifier.align(Alignment.CenterStart) // ✅ FIX

                ) {
                    Icon(Icons.Default.ArrowBack, null)
                }
                Text("Step 4 of 5",
                    modifier = Modifier.align(Alignment.Center), // ✅ center text
                    fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(14.dp))

            // 🔥 PROGRESS BAR (FIXED)
            EventProgressBar(
                progress = 0.8f,
                label = "80% completed",
                value = "80%"
            )

            Spacer(Modifier.height(32.dp))

            Row {
                Text(
                    "What's your ",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    "budget?",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PrimaryOrange
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                "Enter the estimated budget for your event.",
                fontSize = 14.sp,
                color = Color(0xFF666666)
            )

            Spacer(Modifier.height(32.dp))

            // 💰 INPUT
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF3E8), RoundedCornerShape(20.dp))
                    .padding(horizontal = 20.dp, vertical = 22.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        "₹",
                        fontSize = 24.sp,
                        color = PrimaryOrange,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.width(10.dp))

                    BasicTextField(
                        value = budget,
                        onValueChange = {
                            budget = it.filter { c -> c.isDigit() }
                        },
                        textStyle = TextStyle(
                            fontSize = 22.sp,
                            color = Color.Black
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        decorationBox = { inner ->
                            if (budget.isEmpty()) {
                                Text(
                                    "Enter amount",
                                    color = PrimaryOrange.copy(alpha = 0.5f),
                                    fontSize = 22.sp
                                )
                            }
                            inner()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.weight(1f))
        }
    }
}