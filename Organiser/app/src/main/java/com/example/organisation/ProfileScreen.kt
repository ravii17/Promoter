package com.example.organisation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    userName: String,
    userEmail: String,
    userPhone: String,
    userCity: String,
    userBio: String,
    onEditClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        
        // Header
        Text(
            text = "My Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A2E)
        )
        
        Spacer(modifier = Modifier.height(30.dp))
        
        // Profile Picture and Basic Info
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFCCBC))
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp).align(Alignment.Center),
                    tint = Color.White
                )
            }
            
            Spacer(modifier = Modifier.width(20.dp))
            
            Column {
                Text(
                    text = userName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1A1A2E)
                )
                Text(
                    text = userCity,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile",
                    tint = Color(0xFFFF9800)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(40.dp))
        
        // Information Section
        ProfileInfoItem(icon = Icons.Default.Email, label = "Email", value = userEmail)
        ProfileInfoItem(icon = Icons.Default.Phone, label = "Phone", value = userPhone)
        ProfileInfoItem(icon = Icons.Default.LocationOn, label = "Location", value = userCity)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Bio Section
        Text(
            text = "About Me",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A2E)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (userBio.isNotEmpty()) userBio else "No bio provided.",
            fontSize = 15.sp,
            color = Color.Gray,
            lineHeight = 22.sp
        )
        
        Spacer(modifier = Modifier.height(40.dp))
        
        // Settings/Actions
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFF5F5F5)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                SettingsItem(icon = Icons.Default.Settings, title = "Account Settings")
                SettingsItem(icon = Icons.Default.Notifications, title = "Notification Preferences")
                SettingsItem(icon = Icons.Default.Lock, title = "Privacy & Security")
                SettingsItem(icon = Icons.AutoMirrored.Filled.ExitToApp, title = "Log Out", color = Color.Red)
            }
        }
    }
}

@Composable
fun ProfileInfoItem(icon: ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFFF9800),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, fontSize = 12.sp, color = Color.Gray)
            Text(text = value, fontSize = 16.sp, color = Color(0xFF1A1A2E), fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun SettingsItem(icon: ImageVector, title: String, color: Color = Color(0xFF1A1A2E)) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 16.sp, color = color, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
    }
}
