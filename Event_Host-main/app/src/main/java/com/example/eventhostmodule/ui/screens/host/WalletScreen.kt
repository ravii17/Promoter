package com.example.eventhostmodule.ui.screens.host

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.eventhostmodule.ui.components.BottomBar
import com.example.eventhostmodule.ui.theme.*

@Composable
fun WalletScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(HomeBackgroundLight)
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                "Wallet",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = HomeTextPrimary
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryOrange)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Total Balance", color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "₹25,000",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Recent Transactions",
                fontWeight = FontWeight.SemiBold,
                color = HomeTextPrimary
            )

            Spacer(modifier = Modifier.height(10.dp))

            repeat(3) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Event Payment")
                        Text("+ ₹5,000", color = PrimaryOrange)
                    }
                }
            }
        }
    }
}