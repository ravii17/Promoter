package com.example.eventhostmodule.ui.screens.host

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.eventhostmodule.navigation.Screen
import com.example.eventhostmodule.ui.theme.PrimaryOrange

@Composable
fun MatchDetailScreen(navController: NavController) {

    val orange = Color(0xFFF47B20)
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = {
            // ✅ Sticky bottom buttons — always visible
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Button(
                    onClick = { navController.navigate(Screen.ConfirmBooking.route)},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = orange)
                ) {
                    Text(
                        "Accept this company",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }

                Spacer(Modifier.height(10.dp))

                // ✅ "See more options" is plain text centered, not a button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "See more options",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFF111111)
                    )
                }
            }
        },
        containerColor = Color(0xFFFDF7F2)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)   // ✅ scrollable
                .padding(horizontal = 16.dp)
        ) {

            Spacer(Modifier.height(12.dp))

            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, null, tint = Color(0xFF111111))
                }
                Spacer(Modifier.weight(1f))
                Text("Match Detail", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))

            // Title
            Text(
                "We found a great match for your event 🎉",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF111111),
                lineHeight = 28.sp
            )

            Spacer(Modifier.height(6.dp))

            Text(
                "Based on your preferences and availability",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(Modifier.height(16.dp))

            // ✅ Card with image ON TOP (rounded top corners only on image)
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column {

                    // ✅ Full-width image, clipped to top corners of card
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1523438885200-e635ba2c371e",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    Column(modifier = Modifier.padding(16.dp)) {

                        // Name + Rating
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Grand Events Co.",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF111111),
                                modifier = Modifier.weight(1f)
                            )
                            // ✅ Rating badge
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .background(Color(0xFFFFF3E8), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    Icons.Default.Star,
                                    null,
                                    tint = orange,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    "4.8",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF111111)
                                )
                            }
                        }

                        Spacer(Modifier.height(6.dp))

                        // Location
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.LocationOn,
                                null,
                                tint = orange,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                " Mumbai • 500+ events",
                                fontSize = 13.sp,
                                color = Color(0xFF555555)
                            )
                        }

                        Spacer(Modifier.height(14.dp))

                        // ✅ Tags — orange border + orange text
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("Wedding", "Corporate", "Premium").forEach { tag ->
                                Box(
                                    modifier = Modifier
                                        .border(1.dp, orange, RoundedCornerShape(20.dp))
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        tag,
                                        fontSize = 12.sp,
                                        color = orange,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(14.dp))

                        // ✅ Price box with money icon
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFE9F7EF), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // Money icon box
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            Color(0xFFD4EDDA),
                                            RoundedCornerShape(8.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.AccountBalanceWallet,
                                        null,
                                        tint = Color(0xFF1E7F4F),
                                        modifier = Modifier.size(22.dp)
                                    )
                                }

                                Spacer(Modifier.width(12.dp))

                                Column {
                                    Text(
                                        "Estimated: ₹45,000 - ₹55,000",
                                        color = Color(0xFF1E7F4F),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Spacer(Modifier.height(2.dp))
                                    // ✅ Green checkmark + text
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            Icons.Default.CheckCircle,
                                            null,
                                            tint = Color(0xFF1E7F4F),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(Modifier.width(4.dp))
                                        Text(
                                            "Available on your date",
                                            fontSize = 12.sp,
                                            color = Color(0xFF1E7F4F)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(14.dp))

                        // View full profile
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "View full profile",
                                color = orange,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Why this match
            Text(
                "Why this match?",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF111111)
            )

            Spacer(Modifier.height(16.dp))

            // ✅ WhyItem with orange circle + checkmark
            WhyMatchItem(
                title = "Top-rated in Mumbai",
                subtitle = "Consistently rated 4.5+ by hosts for quality and punctuality."
            )
            WhyMatchItem(
                title = "Expertise in Corporate Events",
                subtitle = "Handled over 200 large-scale corporate galas this year."
            )
            WhyMatchItem(
                title = "Within your budget",
                subtitle = "Their typical pricing aligns perfectly with your ₹50k range."
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

// ✅ WhyItem with orange circle checkmark matching the UI
@Composable
fun WhyMatchItem(title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Orange circle with checkmark
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color(0xFFFFF3E8), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Check,
                null,
                tint = Color(0xFFF47B20),
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(Modifier.width(14.dp))

        Column {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color(0xFF111111)
            )
            Spacer(Modifier.height(3.dp))
            Text(
                subtitle,
                fontSize = 13.sp,
                color = Color(0xFF666666),
                lineHeight = 18.sp
            )
        }
    }
}