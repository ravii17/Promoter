package com.example.organisation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class PaymentSummaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PaymentSummaryScreen(
                    onBackClick = { finish() },
                    onConfirmClick = {
                        val intent = Intent(this, ApplicantsActivity::class.java)
                        startActivity(intent)
                    },
                    onEditClick = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentSummaryScreen(
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    onEditClick: () -> Unit
) {
    val orangePrimary = Color(0xFFFF8A00)
    val textDark = Color(0xFF1A1A2E)
    val textGray = Color(0xFF7D7D7D)
    val lightOrange = Color(0xFFFFF3E0)
    val lightBlue = Color(0xFFE3F2FD)
    val bluePrimary = Color(0xFF2196F3)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Organizer", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = orangePrimary)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFFBFBFB)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Payment Summary",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = textDark,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Crew Type Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(lightOrange),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.BusinessCenter,
                                contentDescription = null,
                                tint = orangePrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "CREW TYPE", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFA1887F))
                            Text(text = "Event Staff", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textDark)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = Color(0xFFF5F5F5))
                    Spacer(modifier = Modifier.height(16.dp))

                    SummaryRow("Total Count", "5 Crew Members")
                    SummaryRow("Breakdown", "3 Male, 2 Female")
                    SummaryRow("Rate", "₹500 / hr")
                    SummaryRow("Duration", "8 Hours")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Total Payable Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Total Payable Amount", fontSize = 14.sp, color = Color(0xFF8D6E63), fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "₹20,000", fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, color = textDark)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Total amount to be paid to the Promoter account for the selected crew and duration.",
                        fontSize = 13.sp,
                        color = textGray,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Secure Holding Banner
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = lightBlue.copy(alpha = 0.5f),
                border = null
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = bluePrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = "Secure Holding", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1565C0))
                        Text(
                            text = "Payment will be held securely in escrow and released to the crew only after job completion.",
                            fontSize = 13.sp,
                            color = Color(0xFF1976D2),
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Confirm Button
            Button(
                onClick = onConfirmClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = orangePrimary)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Confirm & Publish Job", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                }
            }

            TextButton(
                onClick = onEditClick,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(text = "Edit details", color = orangePrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 15.sp, color = Color(0xFF9E9E9E))
        Text(text = value, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
    }
}
