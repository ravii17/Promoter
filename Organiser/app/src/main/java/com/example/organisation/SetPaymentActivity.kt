package com.example.organisation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SetPaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SetPaymentScreen(onBackClick = { finish() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetPaymentScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val orangePrimary = Color(0xFFFF8A00)
    val textDark = Color(0xFF1A1A2E)
    val textGray = Color(0xFF7D7D7D)

    var pricePerCrew by remember { mutableStateOf("") }
    var paymentType by remember { mutableStateOf("Per day") }
    var bonus by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Organizer", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFFBFBFB),
        bottomBar = {
            Box(modifier = Modifier.padding(24.dp)) {
                Button(
                    onClick = {
                        val intent = Intent(context, PaymentSummaryActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = orangePrimary)
                ) {
                    Text("Next", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Set Payment",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = textDark
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Main Payment Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(0.dp)) {
                    // Price Input
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Price per crew member",
                            fontSize = 14.sp,
                            color = textGray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "₹",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = textDark
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            TextField(
                                value = pricePerCrew,
                                onValueChange = { pricePerCrew = it },
                                placeholder = { Text("0", fontSize = 24.sp, color = Color.LightGray) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                ),
                                textStyle = LocalTextStyle.current.copy(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textDark
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    HorizontalDivider(color = Color(0xFFF5F5F5))

                    // Payment Type Selector
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Payment type",
                            fontSize = 14.sp,
                            color = textGray,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
                                .padding(4.dp)
                        ) {
                            PaymentTypeButton(
                                text = "Per day",
                                isSelected = paymentType == "Per day",
                                onClick = { paymentType = "Per day" },
                                modifier = Modifier.weight(1f)
                            )
                            PaymentTypeButton(
                                text = "Per hour",
                                isSelected = paymentType == "Per hour",
                                onClick = { paymentType = "Per hour" },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Bonus Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Bonus / Incentive (Optional)",
                        fontSize = 14.sp,
                        color = textGray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "₹",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = textDark
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = bonus,
                            onValueChange = { bonus = it },
                            placeholder = { Text("0", fontSize = 20.sp, color = Color.LightGray) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = textDark
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Info Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color(0xFF94A3B8),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "This amount will be shown to crew members while applying. Make sure it's accurate to attract the right talent.",
                    fontSize = 14.sp,
                    color = Color(0xFF64748B),
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun PaymentTypeButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(
                if (isSelected) Color.White else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .shadow(
                elevation = if (isSelected) 2.dp else 0.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color(0xFF1A1A2E) else Color(0xFF64748B)
        )
    }
}
