package com.example.organisation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SpendingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SpendingScreen(
                    onBackClick = { finish() },
                    onHomeClick = { 
                        val intent = Intent(this, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        startActivity(intent)
                    },
                    onApplicantsClick = {
                        startActivity(Intent(this, ApplicantsActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

data class Transaction(
    val id: Int,
    val title: String,
    val crewCount: Int,
    val date: String,
    val amount: String,
    val status: String,
    val icon: ImageVector,
    val iconBg: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpendingScreen(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onApplicantsClick: () -> Unit
) {
    val orangePrimary = Color(0xFFFF8A00)
    val textDark = Color(0xFF1A1A2E)
    
    val history = listOf(
        Transaction(1, "Music Festival Staffing", 12, "Oct 12", "-$1,200.00", "PAID", Icons.Default.MusicNote, Color(0xFFFFF3E0)),
        Transaction(2, "Tech Conference 2024", 8, "Oct 15", "-$850.00", "PENDING", Icons.Default.Devices, Color(0xFFE3F2FD)),
        Transaction(3, "Summer Gala Night", 15, "Oct 08", "-$2,400.00", "PAID", Icons.Default.Celebration, Color(0xFFF3E5F5)),
        Transaction(4, "City Marathon Logistics", 40, "Sep 30", "-$5,000.00", "PAID", Icons.Default.SportsScore, Color(0xFFFFF9C4))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Spending Overview", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textDark) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = textDark)
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More", tint = textDark)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = { 
            BottomNavigationBar(
                selectedIndex = 2,
                onIndexSelected = { index ->
                    when (index) {
                        0 -> onHomeClick()
                        1 -> onApplicantsClick()
                        // 2 is current
                        // 3 -> Profile
                    }
                }
            ) 
        },
        containerColor = Color(0xFFF8F9FA)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                MainSpendingCard()
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    MiniSummaryCard("THIS MONTH", "$3,200.00", Modifier.weight(1f))
                    MiniSummaryCard("PENDING", "$850.00", Modifier.weight(1f), isPending = true)
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
                VerificationBanner()
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Payment History", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textDark)
                    Text("See All", fontSize = 14.sp, color = orangePrimary, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(history) { transaction ->
                HistoryItem(transaction)
                Spacer(modifier = Modifier.height(12.dp))
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun MainSpendingCard() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("TOTAL SPENT", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray, letterSpacing = 0.5.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("$12,450.00", fontSize = 34.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1A1A2E))
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.TrendingUp, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("4.2% from last month", fontSize = 12.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun MiniSummaryCard(label: String, amount: String, modifier: Modifier, isPending: Boolean = false) {
    Surface(
        modifier = modifier.shadow(2.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray, letterSpacing = 0.5.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                amount,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (isPending) Color(0xFFFF8A00) else Color(0xFF1A1A2E)
            )
        }
    }
}

@Composable
fun VerificationBanner() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFF1F8E9) // Light green tint
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Bank Account Verified", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
                Text("Your payouts are active", fontSize = 12.sp, color = Color(0xFF4CAF50))
            }
            Text(
                "Manage",
                color = Color(0xFFFF8A00),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun HistoryItem(transaction: Transaction) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(transaction.iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(transaction.icon, contentDescription = null, tint = Color(0xFFFF8A00), modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(transaction.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
                Text("${transaction.crewCount} Crew • ${transaction.date}", fontSize = 12.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(transaction.amount, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (transaction.status == "PAID") Color(0xFFE8F5E9) else Color(0xFFFFF3E0)
                ) {
                    Text(
                        text = transaction.status,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (transaction.status == "PAID") Color(0xFF2E7D32) else Color(0xFFFF8A00)
                    )
                }
            }
        }
    }
}
