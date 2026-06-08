package com.example.eventhostmodule.ui.screens.host

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eventhostmodule.ui.components.CustomTextField
import com.example.eventhostmodule.ui.theme.HomeBackgroundLight
import com.example.eventhostmodule.ui.theme.HomeTextPrimary
import com.example.eventhostmodule.ui.theme.PrimaryOrange
import androidx.compose.runtime.*
import com.example.eventhostmodule.navigation.Screen
import androidx.compose.ui.platform.LocalContext
import com.example.eventhostmodule.data.local.SharedPrefManager

@Composable
fun CompleteProfileScreen(
    navController: NavController
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    val progress = 0.5f // Step 1 → 50%

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackgroundLight)
            .padding(20.dp)
    ) {

        // 🔙 Back Button
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 🔹 Step + Progress
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "STEP 1 OF 2",
                fontSize = 12.sp,
                color = Color(0xFFB07A3A), // brownish
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "50%",
                fontSize = 14.sp,
                color = PrimaryOrange,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔥 Progress Bar
        val animatedProgress by animateFloatAsState(
            targetValue = 0.5f,
            animationSpec = tween(800),
            label = ""
        )
        Spacer(modifier = Modifier.height(8.dp))

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        {

            val progress = 0.5f
            val barWidth = maxWidth * progress

            // Background track
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFE0E0E0))
            )

            // Progress fill
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(barWidth)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFFF7A00))
            )

            // End dot
            Box(
                modifier = Modifier
                    .offset(
                        x = if (barWidth > 5.dp) barWidth - 5.dp else 0.dp
                    )
                    .size(10.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFFF7A00))
            )
        }
       /* LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(50)),
            color = PrimaryOrange,
            trackColor = Color(0xFFE0E0E0)
        ) */


        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 Heading
        Text(
            text = "Complete your profile in 2 minutes",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = HomeTextPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tell us a little about yourself",
            fontSize = 16.sp,
            color = Color(0xFFB07A3A)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 Inputs
        CustomTextField(
            label = "Full Name",
            value = name,
            placeholder = "e.g. abc xyz",
            onValueChange = { name = it }
        )
        val isPhoneValid = phone.matches(Regex("^\\+91\\s\\d{10}$"))
        CustomTextField(
            label = "Mobile Number",
            value = phone,
            placeholder = "+91 98765 43210",
            onValueChange = { phone = it },
            trailingIcon = {
                if (isPhoneValid) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50)
                    )
                }
            }
        )
        if (!isPhoneValid && phone.isNotEmpty()) {
            Text("Enter valid phone number", color = Color.Red, fontSize = 12.sp)
        }

        val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        CustomTextField(
            label = "Email ID",
            value = email,
            placeholder = "abc@example.com",
            onValueChange = { email = it } ,
                    trailingIcon = {
                if (isEmailValid) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50)
                    )
                }
            }
        )
        if (!isEmailValid && email.isNotEmpty()) {
            Text("Enter valid email", color = Color.Red, fontSize = 12.sp)
        }

        CustomTextField(
            label = "City",
            value = city,
            placeholder = "Search your city",
            onValueChange = { city = it },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFFB07A3A)
                )
            }
        )





        Spacer(modifier = Modifier.weight(1f))

        // 🔥 Next Button
        var showError by remember { mutableStateOf(false) }
        if (showError) {
            Text(
                "Please fill all fields",
                color = Color.Red,
                fontSize = 12.sp
            )
        }
        Button(
            onClick = {
                val isValid = name.isNotBlank()
                        && isPhoneValid
                        && isEmailValid
                        && city.isNotBlank()

                if (isValid) {
                    SharedPrefManager.getInstance(context).updateUserName(name)
                    navController.navigate(Screen.CompleteProfilePhoto.route)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
        ) {
            Text("Next", color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "By continuing, you agree to our Terms of Service and Privacy Policy.",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}