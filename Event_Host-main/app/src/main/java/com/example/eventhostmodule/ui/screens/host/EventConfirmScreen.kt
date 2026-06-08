package com.example.eventhostmodule.ui.screens.host

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.eventhostmodule.data.model.EventViewModel
import com.example.eventhostmodule.navigation.Routes
import com.example.eventhostmodule.navigation.Screen
import com.example.eventhostmodule.ui.theme.PrimaryOrange

@Composable
fun EventConfirmScreen(
    navController: NavController,
    viewModel: EventViewModel
) {
    val data by viewModel.eventData.collectAsState()
    val orange = Color(0xFFF47B20)
    val bgColor = Color(0xFFF5F5F0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {

        // ── HEADER ──────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(bgColor)
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, null, tint = Color(0xFF111111))
            }
            Text(
                "Confirm your event information",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color(0xFF111111)
            )
        }

        // ── PROGRESS ─────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(bgColor)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Final Step", fontSize = 13.sp, color = Color(0xFF555555))
                Text("100%", fontSize = 13.sp, color = Color(0xFF555555))
            }
            Spacer(Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = 1f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(99.dp)),
                color = orange,
                trackColor = Color(0xFFFFE0C2)
            )
        }

        Spacer(Modifier.height(8.dp))

        // ── SCROLLABLE CONTENT ───────────────────────────────
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {

            Spacer(Modifier.height(8.dp))

            // ── EVENT OVERVIEW ──────────────────────────────
            ConfirmSectionHeader(title = "Event Overview",  onEdit = {
                navController.navigate(Routes.EVENT_STEP1) {
                    popUpTo(Routes.EVENT_CONFIRM) { inclusive = true }
                }
            })

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            data.eventName.ifEmpty { "Event Name" },
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp,
                            color = Color(0xFF111111)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            data.occasion.ifEmpty { "Occasion" },
                            fontSize = 14.sp,
                            color = Color(0xFF888888)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            data.date.ifEmpty { "Date not selected" },
                            fontSize = 14.sp,
                            color = Color(0xFF888888)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            data.location.ifEmpty { "Location" },
                            fontSize = 14.sp,
                            color = Color(0xFF888888)
                        )
                    }

                    Spacer(Modifier.width(12.dp))

                    // ✅ Rounded image
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1511578314322-379afb476865",
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── REQUIREMENTS ────────────────────────────────
            ConfirmSectionHeader(title = "Requirements", onEdit = { navController.navigate(Routes.EVENT_STEP3) {
                popUpTo(Routes.EVENT_CONFIRM) { inclusive = true }
            }})

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "SELECTED SERVICES",
                        fontSize = 11.sp,
                        color = Color(0xFF888888),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(Modifier.height(12.dp))

                    // ✅ Wrap chips in FlowRow
                    val services = if (data.services.isEmpty())
                        listOf("Catering", "Security", "Photography", "Stage Lighting")
                    else data.services

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        services.forEach { service ->
                            // ✅ Orange chip with checkmark
                            Row(
                                modifier = Modifier
                                    .background(
                                        Color(0xFFFFF3E8),
                                        RoundedCornerShape(20.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Check,
                                    null,
                                    tint = orange,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    service,
                                    fontSize = 13.sp,
                                    color = orange,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── BUDGET ──────────────────────────────────────
            ConfirmSectionHeader(title = "Budget", onEdit = {navController.navigate(Routes.EVENT_STEP4) {
                popUpTo(Routes.EVENT_CONFIRM) { inclusive = true }
            } })

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "ESTIMATED TOTAL",
                            fontSize = 11.sp,
                            color = Color(0xFF888888),
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            // ✅ Format with .00
                            "₹${
                                if (data.budget.isNotEmpty())
                                    "%,.2f".format(data.budget.toDoubleOrNull() ?: 0.0)
                                else "50,000.00"
                            }",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF111111)
                        )
                    }

                    // ✅ Wallet icon in orange box
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(Color(0xFFFFF3E8), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("💵", fontSize = 22.sp)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }

        // ── BOTTOM ACTIONS ───────────────────────────────────
        Column(
            modifier = Modifier
                .background(bgColor)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ✅ Confirm button
            Button(
                onClick = { navController.navigate(Screen.MatchingStatus.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = orange,
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Confirm & Continue",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(12.dp))

            // ✅ "Back" as plain bold text, not a button
            Text(
                "Back",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color(0xFF111111),
                modifier = Modifier
                    .clickable { navController.popBackStack() }
                    .padding(vertical = 4.dp)
            )

            Spacer(Modifier.height(12.dp))

            // ✅ Disclaimer text
            Text(
                "By clicking \"Confirm & Continue\", you agree to the\nterms of service and host guidelines.",
                fontSize = 12.sp,
                color = Color(0xFF888888),
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )

            Spacer(Modifier.height(8.dp))
        }
    }
}

// ✅ Section header with Edit in orange
@Composable
fun ConfirmSectionHeader(title: String, onEdit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = Color(0xFF111111)
        )
        Text(
            "Edit",
            color = Color(0xFFF47B20),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { onEdit() }
        )
    }
}