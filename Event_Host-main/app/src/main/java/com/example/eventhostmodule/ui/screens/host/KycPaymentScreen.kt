package com.example.eventhostmodule.ui.screens.host

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eventhostmodule.navigation.Screen
import com.example.eventhostmodule.ui.components.CustomField
import com.example.eventhostmodule.ui.theme.*
import com.example.eventhostmodule.utils.PrefManager

@Composable
fun KycPaymentScreen(navController: NavController) {

    var selectedTab by remember { mutableStateOf("Card") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F0))
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {

        // 🔽 Scrollable content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {

            // ── Header (MATCH AADHAAR/PAN) ──
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(Icons.Default.ArrowBack, null, tint = PrimaryOrange)
                }

                Text(
                    text = "Add Payment Method",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF1A1A1A)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Title ──
            Text(
                "Setup Payment",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Add a payment method to pay crew members for your events.",
                color = Color(0xFF777777),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Tabs (MATCH UI) ──
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFFEDE3D8))
                    .padding(4.dp)
            ) {

                listOf("Card", "UPI", "Net Banking").forEach { tab ->

                    val isSelected = selectedTab == tab

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(50.dp))
                            .background(if (isSelected) Color.White else Color.Transparent)
                            .clickable { selectedTab = tab }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            tab,
                            color = if (isSelected) PrimaryOrange else Color(0xFF888888),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── CONTENT ──
            when (selectedTab) {
                "Card" -> CardSectionUI()
                "UPI" -> UpiSectionUI()
                "Net Banking" -> NetBankingUI()
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // ── Footer + Button ──
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F0))
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🔒 Encrypted text
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🔒")
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    "Encrypted connection",
                    color = Color(0xFF777777),
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
            val context = LocalContext.current


            Button(
                onClick = {
                    PrefManager.setKycDone(context, true)

                    navController.navigate(Screen.HostHome.route) {
                        popUpTo(Screen.HostHome.route) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryOrange
                )
            ) {
                Text(
                    when (selectedTab) {
                        "UPI" -> "Verify & Save UPI"
                        "Net Banking" -> "Save Bank Account"
                        else -> "Save Payment Method"
                    },
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun CardSectionUI() {

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {

        CustomField("CARD HOLDER NAME", "John Doe")

        CustomField("CARD NUMBER", "0000 0000 0000 0000")

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                CustomField("EXPIRY DATE", "MM/YY")
            }
            Box(modifier = Modifier.weight(1f)) {
                CustomField("CVV", "***")
            }
        }
    }
}

@Composable
fun UpiSectionUI() {

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

        CustomField("UPI ID", "name@upi")

        Text(
            "We support Google Pay, PhonePe, Paytm, BHIM.",
            color = Color(0xFF777777),
            fontSize = 13.sp
        )
    }
}

@Composable
fun NetBankingUI() {

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {

        CustomField("SELECT BANK", "Select your bank")

        CustomField("ACCOUNT HOLDER NAME", "As per bank records")

        CustomField("ACCOUNT NUMBER", "Enter account number")

        CustomField("IFSC CODE", "HDFC0001234")
    }
}