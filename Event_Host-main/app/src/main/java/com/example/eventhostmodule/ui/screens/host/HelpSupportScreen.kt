package com.example.eventhostmodule.ui.screens.host

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eventhostmodule.ui.theme.HomeBackgroundLight
import com.example.eventhostmodule.ui.theme.HomeTextPrimary
import com.example.eventhostmodule.ui.theme.HomeTextSecondary
import com.example.eventhostmodule.ui.theme.PrimaryOrange

@Composable
fun HelpSupportScreen(navController: NavController) {
    val faqs = listOf(
        "How do I complete my KYC?" to "Navigate to your Profile screen and click on 'Complete KYC' under the Account section.",
        "How do I contact vendors?" to "Once you match with vendors for your event, you can use the Chat feature to contact them directly.",
        "Can I change my payment settings?" to "Yes, go to Profile > Payment Settings to manage your bank and UPI details."
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackgroundLight)
            .padding(top = 16.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = HomeTextPrimary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Help & Support",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = HomeTextPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Contact Us",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Email, contentDescription = null, tint = PrimaryOrange)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("support@eventhost.com", fontSize = 16.sp, color = HomeTextPrimary)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = PrimaryOrange)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("+91 1800-123-4567", fontSize = 16.sp, color = HomeTextPrimary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Frequently Asked Questions",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(faqs) { (question, answer) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = question,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = HomeTextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = answer,
                            fontSize = 14.sp,
                            color = HomeTextSecondary,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}
