package com.example.organisation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class NotificationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                NotificationScreen(
                    onBackClick = { finish() },
                    onNavigateToApplications = {
                        startActivity(Intent(this, ApplicantsActivity::class.java))
                    },
                    onNavigateToEarnings = {
                        startActivity(Intent(this, SpendingActivity::class.java))
                    }
                )
            }
        }
    }
}

enum class NotificationType {
    APPLICATION, TASK, PAYMENT, ROSTER
}

data class AppNotification(
    val id: Int,
    val title: String,
    val subtitle: String,
    val time: String,
    var isUnread: Boolean,
    val type: NotificationType
)

@Composable
fun NotificationScreen(
    onBackClick: () -> Unit,
    onNavigateToApplications: () -> Unit,
    onNavigateToEarnings: () -> Unit
) {
    var notifications by remember {
        mutableStateOf(
            listOf(
                AppNotification(1, "New Application", "John Doe applied for 'Stage Hand - Summer Fest'", "2m", true, NotificationType.APPLICATION),
                AppNotification(2, "Job Completed", "The task 'Rigging Setup' has been marked as finished", "1h", true, NotificationType.TASK),
                AppNotification(3, "Payment Successful", "Payment of $450.00 processed for 'Tech Crew'", "3h", false, NotificationType.PAYMENT),
                AppNotification(4, "New Application", "Sarah Smith applied for 'Lighting Assistant'", "5h", false, NotificationType.APPLICATION),
                AppNotification(5, "Roster Update", "All shifts for 'Main Stage' are now fully staffed.", "Yesterday", false, NotificationType.ROSTER)
            )
        )
    }

    val orangePrimary = Color(0xFFFF8A00)

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 32.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Notifications",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A2E)
                    )
                    if (notifications.isNotEmpty()) {
                        Text(
                            text = "Mark all as read",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = orangePrimary,
                            modifier = Modifier.clickable {
                                notifications = notifications.map { it.copy(isUnread = false) }
                            }
                        )
                    }
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(selectedIndex = 0) // Keep Home highlighted as per requirement
        },
        containerColor = Color.White
    ) { paddingValues ->
        if (notifications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.NotificationsNone,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No notification",
                        fontSize = 18.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                val todayNotifications = notifications.filter { it.time.contains("m") || it.time.contains("h") }
                val earlierNotifications = notifications.filter { !it.time.contains("m") && !it.time.contains("h") }

                if (todayNotifications.isNotEmpty()) {
                    item {
                        SectionHeader("TODAY")
                    }
                    items(todayNotifications) { notification ->
                        NotificationCard(
                            notification = notification,
                            onClick = {
                                when (notification.type) {
                                    NotificationType.APPLICATION -> onNavigateToApplications()
                                    NotificationType.PAYMENT -> onNavigateToEarnings()
                                    else -> {} // Handle others
                                }
                            }
                        )
                    }
                }

                if (earlierNotifications.isNotEmpty()) {
                    item {
                        SectionHeader("EARLIER")
                    }
                    items(earlierNotifications) { notification ->
                        NotificationCard(
                            notification = notification,
                            onClick = {
                                when (notification.type) {
                                    NotificationType.APPLICATION -> onNavigateToApplications()
                                    NotificationType.PAYMENT -> onNavigateToEarnings()
                                    else -> {}
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Gray,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
    )
}

@Composable
fun NotificationCard(notification: AppNotification, onClick: () -> Unit) {
    val backgroundColor = if (notification.isUnread) Color(0xFFFFF8F0) else Color.White
    val icon = when (notification.type) {
        NotificationType.APPLICATION -> Icons.Default.Person
        NotificationType.TASK -> Icons.Default.CheckCircle
        NotificationType.PAYMENT -> Icons.Default.AccountBalanceWallet
        NotificationType.ROSTER -> Icons.Default.Assignment
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        shadowElevation = if (notification.isUnread) 0.dp else 1.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon in rounded square
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF1A1A2E),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = notification.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A2E)
                    )
                    Text(
                        text = notification.time,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = notification.subtitle,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.weight(1f)
                    )
                    if (notification.isUnread) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color(0xFFFF8A00), CircleShape)
                        )
                    }
                }
            }
        }
    }
}
