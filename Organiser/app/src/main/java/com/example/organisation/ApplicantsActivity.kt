package com.example.organisation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ApplicantsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ApplicantsScreen(
                    onBackClick = { finish() },
                    onConfirmClick = {
                        val intent = Intent(this, EventDayActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

data class Applicant(
    val id: Int,
    val name: String,
    val rating: Double?,
    val jobs: Int?,
    val isKycVerified: Boolean,
    val hasWorkedWithYou: Boolean,
    val completionRate: Int,
    val distance: String,
    val isSelected: Boolean = false,
    val status: String = "New Applicant"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicantsScreen(onBackClick: () -> Unit, onConfirmClick: () -> Unit) {
    val orangePrimary = Color(0xFFFF8A00)
    val textDark = Color(0xFF1A1A2E)
    val textGray = Color(0xFF7D7D7D)

    val applicants = listOf(
        Applicant(1, "Sarah Jenkins", 4.9, 24, true, true, 98, "2.4 km"),
        Applicant(2, "Michael Chen", 4.5, 10, true, false, 100, "5.1 km", isSelected = true),
        Applicant(3, "Jessica Lee", null, null, false, false, 0, "8.0 km"),
        Applicant(4, "David Kim", 5.0, 42, true, true, 100, "1.1 km")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Applicants", fontWeight = FontWeight.Bold, fontSize = 24.sp) },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFF5F5F5),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(Icons.Default.Tune, contentDescription = "Filter", modifier = Modifier.padding(8.dp))
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            Box(modifier = Modifier.padding(24.dp)) {
                Button(
                    onClick = onConfirmClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = orangePrimary)
                ) {
                    Text("Confirm Selected Crew 1/5", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        },
        containerColor = Color(0xFFFBFBFB)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                JobSummaryCard(orangePrimary)
            }

            item {
                FilterRow()
            }

            items(applicants) { applicant ->
                ApplicantCard(applicant, orangePrimary)
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun JobSummaryCard(orangePrimary: Color) {
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
                Text(
                    text = "Brand Ambassador - Tech Expo",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A2E)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarToday, contentDescription = null, size = 14.dp, tint = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Oct 24, 2023 • 09:00 AM", fontSize = 14.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, size = 14.dp, tint = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Convention Center, Hall B", fontSize = 14.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Job ID: #84920", fontSize = 12.sp, color = Color.LightGray)
                    Text(
                        "View Job Details →",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = orangePrimary
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Surface(
                modifier = Modifier.size(70.dp),
                shape = CircleShape,
                color = Color(0xFFFFF3E0)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("12", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = orangePrimary)
                    Text("APPLICANTS", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = orangePrimary)
                }
            }
        }
    }
}

@Composable
fun FilterRow() {
    val filters = listOf("All", "Verified only", "High rating", "Worked with you")
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(filters) { filter ->
            val isSelected = filter == "All"
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) Color(0xFF1A1A2E) else Color.White,
                border = BorderStroke(1.dp, Color(0xFFEEEEEE)),
                modifier = Modifier.height(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = filter,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun ApplicantCard(applicant: Applicant, orangePrimary: Color) {
    val cardModifier = if (applicant.isSelected) {
        Modifier
            .fillMaxWidth()
            .border(2.dp, orangePrimary, RoundedCornerShape(24.dp))
    } else {
        Modifier.fillMaxWidth()
    }

    Box {
        Card(
            modifier = cardModifier,
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.Top) {
                    // Profile Image
                    Box {
                        Surface(
                            modifier = Modifier.size(60.dp),
                            shape = CircleShape,
                            color = Color.LightGray
                        ) {
                            // Placeholder
                            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.padding(12.dp))
                        }
                        if (applicant.isKycVerified) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = "Verified",
                                tint = Color(0xFF4CAF50),
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.BottomEnd)
                                    .background(Color.White, CircleShape)
                                    .border(2.dp, Color.White, CircleShape)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = applicant.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFF5F5F5)
                            ) {
                                Text(
                                    text = applicant.distance,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        if (applicant.rating != null) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "${applicant.rating}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = orangePrimary)
                                Icon(Icons.Default.Star, contentDescription = null, size = 12.dp, tint = orangePrimary)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "• ${applicant.jobs} jobs", fontSize = 13.sp, color = Color.Gray)
                            }
                        } else {
                            Text(text = applicant.status, fontSize = 13.sp, color = Color.Gray)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            if (applicant.isKycVerified) {
                                Tag("KYC Verified", Color(0xFFE8F5E9), Color(0xFF2E7D32), Icons.Default.CheckCircle)
                            } else {
                                Tag("Pending KYC", Color(0xFFF5F5F5), Color.Gray)
                            }
                            if (applicant.hasWorkedWithYou) {
                                Tag("Worked with you", Color(0xFFE3F2FD), Color(0xFF1976D2), Icons.Default.Groups)
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (applicant.completionRate > 0) "${applicant.completionRate}% Completion Rate" else "Profile incomplete",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color(0xFFF5F5F5))
                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (applicant.isSelected) {
                        Surface(
                            modifier = Modifier.weight(1f).height(40.dp),
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFF8F9FA)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                Icon(Icons.Default.Close, contentDescription = null, size = 16.dp, tint = Color.LightGray)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Reject", color = Color.LightGray, fontSize = 14.sp)
                            }
                        }
                        Button(
                            onClick = { /* TODO */ },
                            modifier = Modifier.weight(1f).height(40.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = orangePrimary)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, size = 16.dp, tint = Color.White)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Selected", fontSize = 14.sp)
                        }
                    } else {
                        OutlinedButton(
                            onClick = { /* TODO */ },
                            modifier = Modifier.weight(1f).height(40.dp),
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(1.dp, Color(0xFFEEEEEE)),
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFF8F9FA))
                        ) {
                            Icon(Icons.Default.Close, contentDescription = null, size = 16.dp, tint = Color(0xFF1A1A2E))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Reject", color = Color(0xFF1A1A2E), fontSize = 14.sp)
                        }
                        OutlinedButton(
                            onClick = { /* TODO */ },
                            modifier = Modifier.weight(1f).height(40.dp),
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(1.dp, Color(0xFFFFF3E0)),
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFFFF3E0))
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, size = 16.dp, tint = orangePrimary)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Accept", color = orangePrimary, fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        if (applicant.isSelected) {
            Surface(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-12).dp),
                shape = RoundedCornerShape(8.dp),
                color = orangePrimary
            ) {
                Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Check, contentDescription = null, size = 12.dp, tint = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Selected", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun Tag(text: String, bgColor: Color, textColor: Color, icon: androidx.compose.ui.graphics.vector.ImageVector? = null) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = bgColor
    ) {
        Row(modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(icon, contentDescription = null, size = 12.dp, tint = textColor)
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(text = text, color = textColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun Icon(imageVector: androidx.compose.ui.graphics.vector.ImageVector, contentDescription: String?, size: androidx.compose.ui.unit.Dp, tint: Color) {
    androidx.compose.material3.Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = Modifier.size(size),
        tint = tint
    )
}
