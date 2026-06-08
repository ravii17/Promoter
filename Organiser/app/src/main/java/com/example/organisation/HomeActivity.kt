package com.example.organisation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.util.Locale

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Check if we arrived here after completing payment
        val isPaymentCompleted = intent.getBooleanExtra("IS_PAYMENT_COMPLETED", false)
        
        // Get user name from SharedPreferences
        val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userName = prefs.getString("userName", "Alex Rivera") ?: "Alex Rivera"
        val userEmail = prefs.getString("userEmail", "alex@example.com") ?: "alex@example.com"
        val userPhone = prefs.getString("userPhone", "+1 234 567 890") ?: "+1 234 567 890"
        val userCity = prefs.getString("userCity", "London, UK") ?: "London, UK"
        val userBio = prefs.getString("userBio", "Event enthusiast and organizer.") ?: "Event enthusiast and organizer."
        
        setContent {
            var currentLocation by remember { mutableStateOf("Fetching location...") }
            val context = LocalContext.current
            val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

            val locationPermissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                    // Permission granted, fetch location
                    fetchLocation(fusedLocationClient, { currentLocation = it })
                } else {
                    currentLocation = "Permission denied"
                }
            }

            LaunchedEffect(Unit) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation(fusedLocationClient, { currentLocation = it })
                } else {
                    locationPermissionLauncher.launch(arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ))
                }
            }

            MaterialTheme {
                DashboardContainer(
                    userName = userName,
                    currentLocation = currentLocation,
                    userEmail = userEmail,
                    userPhone = userPhone,
                    userCity = userCity,
                    userBio = userBio,
                    onCreateEventClick = {
                        startActivity(Intent(this, CreateJobActivity::class.java))
                    },
                    onFinishSetupClick = {
                        startActivity(Intent(this, KYCActivity::class.java))
                    },
                    onApplicantsClick = {
                        startActivity(Intent(this, ApplicantsActivity::class.java))
                    },
                    onSpendingClick = {
                        startActivity(Intent(this, SpendingActivity::class.java))
                    },
                    onNotificationsClick = {
                        startActivity(Intent(this, NotificationActivity::class.java))
                    },
                    isProfileComplete = isPaymentCompleted
                )
            }
        }
    }

    private fun fetchLocation(fusedLocationClient: com.google.android.gms.location.FusedLocationProviderClient, onLocationFetched: (String) -> Unit) {
        try {
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        if (!addresses.isNullOrEmpty()) {
                            val address = addresses[0]
                            val city = address.locality ?: address.subAdminArea ?: "Unknown City"
                            val state = address.adminArea ?: ""
                            onLocationFetched("$city, $state")
                        } else {
                            onLocationFetched("${location.latitude}, ${location.longitude}")
                        }
                    } else {
                        onLocationFetched("Location unavailable")
                    }
                }
                .addOnFailureListener {
                    onLocationFetched("Error fetching location")
                }
        } catch (e: SecurityException) {
            onLocationFetched("Permission error")
        }
    }
}
