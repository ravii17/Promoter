package com.example.organisation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class EventDayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                EventDayScreen(onBackClick = { finish() })
            }
        }
    }
}

data class CrewMember(
    val id: Int,
    val name: String,
    val status: String,
    val timeOrStatus: String,
    val isChecked: Boolean,
    val showActions: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDayScreen(onBackClick: () -> Unit) {
    val orangePrimary = Color(0xFFFF8A00)
    val lightOrange = Color(0xFFFFF3E0)
    val textDark = Color(0xFF1A1A2E)
    val textGray = Color(0xFF7D7D7D)

    val crewList = listOf(
        CrewMember(1, "Sarah Jenkins", "VERIFIED", "11:45 AM", true),
        CrewMember(2, "Michael Chen", "VERIFIED", "11:50 AM", true),
        CrewMember(3, "Jessica Davis", "PENDING", "Late", false, showActions = true),
        CrewMember(4, "Alex Johnson", "APPROVE SELFIE", "", false),
        CrewMember(5, "David Kim", "VERIFIED", "12:05 PM", false)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.MoreHoriz, contentDescription = "More")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = orangePrimary)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Mark Event Started", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
                TextButton(onClick = { }) {
                    Text("Close Attendance", color = Color(0xFF8D6E63), fontWeight = FontWeight.Bold)
                }
            }
        },
        containerColor = Color(0xFFFBFBFB)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            item {
                Text(
                    text = "Event Day",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = textDark
                )
                Text(
                    text = "Saturday, Oct 14",
                    fontSize = 16.sp,
                    color = Color(0xFFA1887F),
                    modifier = Modifier.padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                LiveNowCard(orangePrimary)
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                AttendanceSection(orangePrimary)
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Text(
                    text = "Crew Checklist",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textDark
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(crewList) { crew ->
                CrewChecklistItem(crew, orangePrimary)
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@Composable
fun LiveNowCard(orangePrimary: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Surface(
                    color = Color(0xFFFFF3E0),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Live Now",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = orangePrimary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Main Stage Security",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A2E)
                )
                Text(
                    text = "Central Park • Gate A",
                    fontSize = 14.sp,
                    color = Color(0xFFA1887F)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFF8D6E63)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "12:00 PM - 8:00 PM",
                        fontSize = 14.sp,
                        color = Color(0xFF8D6E63)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFA5D6A7)) // Placeholder for map
            )
        }
    }
}

@Composable
fun AttendanceSection(orangePrimary: Color) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Attendance", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = "12/15 Checked In", fontSize = 14.sp, color = orangePrimary, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { 12f / 15f },
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp)),
            color = orangePrimary,
            trackColor = Color(0xFFE0E0E0)
        )
    }
}

@Composable
fun CrewChecklistItem(crew: CrewMember, orangePrimary: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box {
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = CircleShape,
                        color = Color(0xFFF5F5F5)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.BottomEnd)
                            .background(Color.White, CircleShape)
                            .border(1.dp, Color.White, CircleShape)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = crew.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        StatusTag(crew.status)
                        if (crew.timeOrStatus.isNotEmpty()) {
                            Text(
                                text = " • ${crew.timeOrStatus}",
                                fontSize = 12.sp,
                                color = if (crew.timeOrStatus == "Late") Color.Red else Color.Gray
                            )
                        }
                    }
                }
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, tint = Color.Gray)
                }
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(if (crew.isChecked) orangePrimary else Color.Transparent)
                        .border(1.dp, if (crew.isChecked) orangePrimary else Color.LightGray, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (crew.isChecked) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            if (crew.showActions) {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color(0xFFF5F5F5))
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFF1F0)),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color.Red
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Call Crew", color = Color.Red, fontSize = 13.sp)
                        }
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF8F9FA)),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.PersonAdd,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Replace", color = Color.Black, fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusTag(status: String) {
    val (bgColor, textColor, icon) = when (status) {
        "VERIFIED" -> Triple(Color(0xFFFFF3E0), Color(0xFFFF8A00), Icons.Default.CheckCircle)
        "PENDING" -> Triple(Color(0xFFF5F5F5), Color(0xFF8D6E63), Icons.Default.CameraAlt)
        "APPROVE SELFIE" -> Triple(Color(0xFFE3F2FD), Color(0xFF1976D2), Icons.Default.CheckCircle)
        else -> Triple(Color.LightGray, Color.DarkGray, Icons.Default.Info)
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(10.dp),
                tint = textColor
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = status, color = textColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun Icon(imageVector: androidx.compose.ui.graphics.vector.ImageVector, contentDescription: String?, modifier: Modifier = Modifier, tint: Color = LocalContentColor.current) {
    androidx.compose.material3.Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}
