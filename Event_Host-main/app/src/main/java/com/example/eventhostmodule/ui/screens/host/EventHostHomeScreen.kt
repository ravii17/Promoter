package com.example.eventhostmodule.ui.screens.host

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.example.eventhostmodule.R
import com.example.eventhostmodule.data.model.EventViewModel
import com.example.eventhostmodule.navigation.Screen
import com.example.eventhostmodule.ui.components.StoryCard
import com.example.eventhostmodule.ui.components.BottomBar
import com.example.eventhostmodule.ui.theme.*
import com.example.eventhostmodule.utils.PrefManager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

@Composable
fun EventHostHomeScreen(
    navController: NavController,
    viewModel: EventViewModel,
    profileProgress: Float = 0.5f
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val userName = com.example.eventhostmodule.data.local.SharedPrefManager.getInstance(context).getUser()?.name?.takeIf { it.isNotBlank() } ?: "Guest"

    // ✅ EVENT DATA
    val eventData by viewModel.eventData.collectAsState()

    // ✅ KYC STATE
    var isKycCompleted by remember { mutableStateOf(PrefManager.isKycDone(context)) }

    // ✅ LOCATION STATE
    var userLocation by remember { mutableStateOf("Locating...") }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    // ✅ PERMISSION LAUNCHER
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (isGranted) {
            fetchLocation(context, fusedLocationClient, coroutineScope) { locationStr ->
                userLocation = locationStr
            }
        } else {
            userLocation = "Location access denied"
        }
    }

    LaunchedEffect(navController.currentBackStackEntry) {
        isKycCompleted = PrefManager.isKycDone(context)

        // Automatically check/request permission on screen load
        val hasPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            fetchLocation(context, fusedLocationClient, coroutineScope) { locationStr ->
                userLocation = locationStr
            }
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(HomeBackgroundLight)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            // 🔹 TOP BAR
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_people),
                        contentDescription = null,
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                    )

                    Spacer(Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "Hello, $userName 👋",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = HomeTextPrimary
                        )

                        Spacer(Modifier.height(2.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location",
                                tint = PrimaryOrange,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = userLocation, // Dynamically updates when fetched
                                fontSize = 13.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }

                IconButton(onClick = { navController.navigate(Screen.Notifications.route) }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                }
            }

            Spacer(Modifier.height(20.dp))

            // 🔥 PROFILE CARD
            if (!isKycCompleted) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Complete your profile", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(10.dp))
                        Button(
                            onClick = {
                                navController.navigate(Screen.CompleteProfileIntro.route)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                        ) {
                            Text("Finish Setup")
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
            }

            // 🔥 EVENT OR DEFAULT BANNER
            if (eventData.eventName.isNotEmpty()) {
                Card(
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.sample_event),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(240.dp)
                                .fillMaxWidth()
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(Color.Black.copy(alpha = 0.4f))
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                eventData.occasion.uppercase(),
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                eventData.eventName,
                                color = Color.White,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(10.dp))
                            Row {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.5f))
                                ) {
                                    Text(
                                        eventData.date.ifEmpty { "Date not set" },
                                        modifier = Modifier.padding(8.dp),
                                        color = Color.White
                                    )
                                }
                                Spacer(Modifier.width(10.dp))
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.5f))
                                ) {
                                    Text(
                                        "${eventData.guests} guests",
                                        modifier = Modifier.padding(8.dp),
                                        color = Color.White
                                    )
                                }
                            }
                            Spacer(Modifier.height(14.dp))
                            Button(
                                onClick = {
                                    navController.navigate(Screen.MatchingStatus.route)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                            ) {
                                Text("View Matching Vendors →")
                            }
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(16.dp)
                                .background(PrimaryOrange, RoundedCornerShape(50))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("SEARCHING VENDORS", color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            } else {
                Card(shape = RoundedCornerShape(24.dp)) {
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.sample_event),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(220.dp)
                                .fillMaxWidth()
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(Color.Black.copy(alpha = 0.35f))
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                "Plan your perfect event",
                                color = White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                "Everything you need to host a successful event.",
                                color = White,
                                fontSize = 13.sp
                            )
                            Spacer(Modifier.height(10.dp))
                            Button(
                                onClick = {
                                    if (isKycCompleted) {
                                        navController.navigate(Screen.EventFlow.route)
                                    } else {
                                        Toast.makeText(context, "Complete KYC first", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            ) {
                                Text("Start Planning")
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // 🔹 SUCCESS STORIES
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Success Stories",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = HomeTextPrimary
                )
                Text(
                    "USED BY 2,400+ HOSTS",
                    fontSize = 11.sp,
                    color = PrimaryOrange
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(getStories()) { StoryCard(it) }
            }

            Spacer(Modifier.height(40.dp))

            Text(
                text = "How it works",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = HomeTextPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                HowItem(
                    title = "1. Define Your Vision",
                    desc = "Share your event type, guest count, and preferences.",
                    icon = "📝",
                    isLast = false
                )
                HowItem(
                    title = "2. Browse & Book",
                    desc = "Get matched with verified vendors and venues instantly.",
                    icon = "📦",
                    isLast = false
                )
                HowItem(
                    title = "3. Execute & Enjoy",
                    desc = "Manage everything from our dashboard and host with ease.",
                    icon = "🎉",
                    isLast = true
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// Helper function to fetch and format location
@RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
private fun fetchLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    coroutineScope: CoroutineScope,
    onLocationFetched: (String) -> Unit
) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    @Suppress("DEPRECATION")
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val address = addresses[0]
                        val city = address.locality ?: address.subAdminArea ?: "Unknown"
                        val country = address.countryName ?: ""
                        val finalLocation = if (country.isNotEmpty()) "$city, $country" else city

                        withContext(Dispatchers.Main) {
                            onLocationFetched(finalLocation)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            onLocationFetched("Location not found")
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        onLocationFetched("Failed to get location")
                    }
                }
            }
        } else {
            onLocationFetched("Turn on GPS")
        }
    }.addOnFailureListener {
        onLocationFetched("Location error")
    }
}

// Data model
data class Story(
    val image: Int, val type: String, val title: String, val subtitle: String
)

fun getStories(): List<Story> {
    return listOf(
        Story(R.drawable.sample_event, "WEDDING", "Rohan & Priya’s Big Day", "Saved ₹1.2 Lakhs"),
        Story(R.drawable.sample_event, "CORPORATE", "Tech Summit", "400+ Attendees"),
        Story(R.drawable.sample_event, "BIRTHDAY", "Aarav’s Party", "Fun & Memorable")
    )
}

// How item
@Composable
fun HowItem(
    title: String,
    desc: String,
    icon: String,
    isLast: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // 🔥 Timeline (dot + line)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Outer circle
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        color = PrimaryOrange.copy(alpha = 0.15f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(PrimaryOrange, CircleShape)
                )
            }

            // 🔥 Vertical line (only if not last)
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(40.dp)
                        .background(Color.LightGray.copy(alpha = 0.5f))
                )
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.padding(top = 2.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = HomeTextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = desc,
                fontSize = 13.sp,
                color = HomeTextSecondary
            )
        }
    }
}
