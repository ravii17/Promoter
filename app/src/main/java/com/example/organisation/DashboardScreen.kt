package com.example.organisation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardContainer(
    userName: String,
    currentLocation: String,
    userEmail: String,
    userPhone: String,
    userCity: String,
    userBio: String,
    onCreateEventClick: () -> Unit = {},
    onFinishSetupClick: () -> Unit = {},
    onApplicantsClick: () -> Unit = {},
    onSpendingClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    isProfileComplete: Boolean = false
) {
    var selectedIndex by remember { mutableStateOf(0) }
    
    Scaffold(
        bottomBar = { 
            BottomNavigationBar(
                selectedIndex = selectedIndex,
                onIndexSelected = { index ->
                    selectedIndex = index
                }
            ) 
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedIndex) {
                0 -> DashboardScreenContent(
                    userName = userName,
                    currentLocation = currentLocation,
                    onCreateEventClick = onCreateEventClick,
                    onFinishSetupClick = onFinishSetupClick,
                    onApplicantsClick = onApplicantsClick,
                    onSpendingClick = onSpendingClick,
                    onNotificationsClick = onNotificationsClick,
                    isProfileComplete = isProfileComplete
                )
                3 -> ProfileScreen(
                    userName = userName,
                    userEmail = userEmail,
                    userPhone = userPhone,
                    userCity = userCity,
                    userBio = userBio
                )
                else -> {
                    // Placeholder for other screens
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Screen $selectedIndex Coming Soon")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreenContent(
    userName: String,
    currentLocation: String,
    onCreateEventClick: () -> Unit,
    onFinishSetupClick: () -> Unit,
    onApplicantsClick: () -> Unit,
    onSpendingClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    isProfileComplete: Boolean
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // Top Header Section
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Profile Avatar
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFCCBC))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.Center),
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Welcome back,",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        Text(
                            text = userName,
                            color = Color(0xFF1A1A2E),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
                // Notification Icon
                Surface(
                    modifier = Modifier
                        .size(44.dp)
                        .clickable { onNotificationsClick() },
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_notification),
                            contentDescription = "Notifications",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                        // Red dot badge
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color.Red, CircleShape)
                                .align(Alignment.TopEnd)
                                .offset(x = (-10).dp, y = 10.dp)
                        )
                    }
                }
            }
        }

        // Complete Profile Banner
        if (!isProfileComplete) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xFFFFF4E5), // Light orange background
                    shadowElevation = 0.dp
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color(0xFFFF9800).copy(alpha = 0.2f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Info,
                                contentDescription = null,
                                tint = Color(0xFFFF9800)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Finish Setup",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFFE65100)
                            )
                            Text(
                                "Complete your profile to unlock all features",
                                fontSize = 13.sp,
                                color = Color(0xFFFF9800)
                            )
                        }
                        Button(
                            onClick = onFinishSetupClick,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Text("Finish", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Search Bar
        item {
            Spacer(modifier = Modifier.height(24.dp))
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(54.dp)
                    .shadow(1.dp, RoundedCornerShape(27.dp)),
                placeholder = { Text("Search for jobs, events, or crew...", fontSize = 14.sp, color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    disabledContainerColor = Color(0xFFF5F5F5),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(27.dp)
            )
        }

        // Create New Event Button
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onCreateEventClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(56.dp)
                    .shadow(6.dp, RoundedCornerShape(28.dp), spotColor = Color(0xFFFF9800)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                shape = RoundedCornerShape(28.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_check), contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text("Create New Event", fontWeight = FontWeight.Bold, fontSize = 16.sp, letterSpacing = 0.5.sp)
            }
        }

        // Quick Action Grid (2x2)
        item {
            Spacer(modifier = Modifier.height(28.dp))
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    QuickActionCard(
                        "Post a Job",
                        painterResource(id = R.drawable.ic_post_job),
                        Color(0xFFFF9800),
                        Modifier.weight(1f).clickable { onCreateEventClick() }
                    )
                    QuickActionCard(
                        "My Crew",
                        painterResource(id = R.drawable.ic_my_crew),
                        Color(0xFF2196F3),
                        Modifier.weight(1f).clickable { onApplicantsClick() },
                        "24 Active"
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    QuickActionCard("Dashboard", painterResource(id = R.drawable.ic_dashboard), Color(0xFF9C27B0), Modifier.weight(1f))
                    QuickActionCard("My Events", painterResource(id = R.drawable.ic_my_events), Color(0xFF4CAF50), Modifier.weight(1f))
                }
            }
        }

        // Ongoing Events Section
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Ongoing Events", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1A1A2E))
                Text("See All", color = Color(0xFFFF9800), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { EventCard("Summer Beats Festival", "Main Stadium, LA", Color(0xFFE0F2F1), onClick = onCreateEventClick) }
                item { EventCard("Tech Expo 2024", "Convention Center, NY", Color(0xFFE3F2FD), onClick = onCreateEventClick) }
            }
        }

        // Crew Applications Section
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Crew Applications", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1A1A2E))
                Text("View 12 More", color = Color(0xFFFF9800), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(crewApplications) { app ->
            CrewApplicationCard(app, onReviewClick = onCreateEventClick)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun QuickActionCard(title: String, icon: androidx.compose.ui.graphics.painter.Painter, iconColor: Color, modifier: Modifier = Modifier, subtitle: String? = null) {
    Surface(
        modifier = modifier.aspectRatio(1.1f),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(painter = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(26.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1A1A2E))
            if (subtitle != null) {
                Text(text = subtitle, fontSize = 11.sp, color = Color.Gray, modifier = Modifier.padding(top = 2.dp))
            }
        }
    }
}

@Composable
fun EventCard(title: String, location: String, placeholderColor: Color, onClick: () -> Unit = {}) {
    Surface(
        modifier = Modifier.width(280.dp).clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 3.dp
    ) {
        Column {
            Box(modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .background(placeholderColor)) {
                // Badge
                Surface(
                    modifier = Modifier.padding(12.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF4CAF50)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(6.dp).background(Color.White, CircleShape))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "HAPPENING TODAY",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color(0xFF1A1A2E))
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.ic_location), contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = location, color = Color.Gray, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun CrewApplicationCard(app: CrewApplication, onReviewClick: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(Color(0xFFF5F5F5))) {
                 Icon(
                     painter = painterResource(id = R.drawable.ic_person), 
                     contentDescription = null, 
                     modifier = Modifier.align(Alignment.Center).size(28.dp),
                     tint = Color.LightGray
                 )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = app.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1A1A2E))
                Text(text = "${app.role} • ${app.time}", color = Color.Gray, fontSize = 13.sp)
            }
            Button(
                onClick = onReviewClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFF9C4)),
                contentPadding = PaddingValues(horizontal = 18.dp),
                modifier = Modifier.height(38.dp),
                shape = RoundedCornerShape(19.dp)
            ) {
                Text("Review", color = Color(0xFFFBC02D), fontSize = 13.sp, fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedIndex: Int = 0, onIndexSelected: (Int) -> Unit = {}) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            .shadow(20.dp),
        color = Color.White
    ) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp
        ) {
            NavigationBarItem(
                selected = selectedIndex == 0,
                onClick = { onIndexSelected(0) },
                icon = { Icon(painter = painterResource(id = R.drawable.ic_home), contentDescription = null, modifier = Modifier.size(26.dp)) },
                label = { Text("Home", fontWeight = if (selectedIndex == 0) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFF9800),
                    selectedTextColor = Color(0xFFFF9800),
                    unselectedIconColor = Color.LightGray,
                    unselectedTextColor = Color.LightGray,
                    indicatorColor = Color.Transparent
                )
            )
            NavigationBarItem(
                selected = selectedIndex == 1,
                onClick = { onIndexSelected(1) },
                icon = { Icon(painter = painterResource(id = R.drawable.ic_applications), contentDescription = null, modifier = Modifier.size(26.dp)) },
                label = { Text("Application", fontWeight = if (selectedIndex == 1) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFF9800),
                    selectedTextColor = Color(0xFFFF9800),
                    unselectedIconColor = Color.LightGray,
                    unselectedTextColor = Color.LightGray,
                    indicatorColor = Color.Transparent
                )
            )
            NavigationBarItem(
                selected = selectedIndex == 2,
                onClick = { onIndexSelected(2) },
                icon = { Icon(painter = painterResource(id = R.drawable.ic_wallet), contentDescription = null, modifier = Modifier.size(26.dp)) },
                label = { Text("Spending", fontWeight = if (selectedIndex == 2) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFF9800),
                    selectedTextColor = Color(0xFFFF9800),
                    unselectedIconColor = Color.LightGray,
                    unselectedTextColor = Color.LightGray,
                    indicatorColor = Color.Transparent
                )
            )
            NavigationBarItem(
                selected = selectedIndex == 3,
                onClick = { 
                    onIndexSelected(3) 
                },
                icon = { Icon(painter = painterResource(id = R.drawable.ic_profile), contentDescription = null, modifier = Modifier.size(26.dp)) },
                label = { Text("Profile", fontWeight = if (selectedIndex == 3) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFF9800),
                    selectedTextColor = Color(0xFFFF9800),
                    unselectedIconColor = Color.LightGray,
                    unselectedTextColor = Color.LightGray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

data class CrewApplication(val name: String, val role: String, val time: String)

val crewApplications = listOf(
    CrewApplication("Jordan Smith", "Sound Engineer", "5 hr ago"),
    CrewApplication("Maya Chen", "Stage Manager", "3 hr ago"),
    CrewApplication("Damian Wright", "Security Head", "1 hr ago")
)
