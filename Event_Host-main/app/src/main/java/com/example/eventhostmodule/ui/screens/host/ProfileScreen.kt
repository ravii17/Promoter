package com.example.eventhostmodule.ui.screens.host

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import com.example.eventhostmodule.navigation.Screen
import com.example.eventhostmodule.ui.components.BottomBar
import com.example.eventhostmodule.ui.theme.*
import com.example.eventhostmodule.data.local.SharedPrefManager

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(HomeBackgroundLight)
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            // ── Gradient Header ──────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(PrimaryOrange, PrimaryOrange.copy(alpha = 0.6f))
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Avatar with white ring
                    Box(
                        modifier = Modifier
                            .size(86.dp)
                            .border(3.dp, Color.White, CircleShape)
                            .padding(3.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val userName = SharedPrefManager.getInstance(context).getUser()?.name?.takeIf { it.isNotBlank() } ?: "Guest"
                    Text(
                        userName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "Event Host",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }

            // ── Stats Row ────────────────────────────────────────────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .offset(y = (-24).dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem("12", "Events")
                    VerticalDivider()
                    StatItem("4.8★", "Rating")
                    VerticalDivider()
                    StatItem("₹2L+", "Saved")
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // ── Section: General ─────────────────────────────────────
            SectionLabel("General")

            ProfileMenuItem(
                icon = Icons.Default.Notifications,
                label = "Notifications",
                subtitle = "Alerts and reminders",
                tint = PrimaryOrange
            ) { navController.navigate(Screen.Notifications.route) }

            ProfileMenuItem(
                icon = Icons.Default.HelpOutline,
                label = "Help & Support",
                subtitle = "FAQs and contact us",
                tint = Color(0xFF9C27B0)
            ) { navController.navigate(Screen.HelpSupport.route) }

            ProfileMenuItem(
                icon = Icons.Default.Info,
                label = "About",
                subtitle = "App version 1.0.0",
                tint = Color.Gray
            ) { navController.navigate(Screen.About.route) }

            Spacer(modifier = Modifier.height(8.dp))

            // ── Logout ────────────────────────────────────────────────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFEBEE)
                ),
                onClick = {
                    SharedPrefManager.getInstance(context).clearUser()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0xFFFFCDD2), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = null,
                            tint = Color(0xFFE53935),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        "Logout",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFE53935)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ── Helpers ───────────────────────────────────────────────────────────────────

@Composable
private fun SectionLabel(title: String) {
    Text(
        text = title.uppercase(),
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Gray,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
        letterSpacing = 1.2.sp
    )
}

@Composable
private fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = HomeTextPrimary)
        Text(label, fontSize = 12.sp, color = HomeTextSecondary)
    }
}

@Composable
private fun VerticalDivider() {
    Box(
        modifier = Modifier
            .height(36.dp)
            .width(1.dp)
            .background(Color.LightGray)
    )
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    label: String,
    subtitle: String,
    tint: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tinted icon bubble
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(tint.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tint,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(label, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = HomeTextPrimary)
                Text(subtitle, fontSize = 12.sp, color = HomeTextSecondary)
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}