package com.example.eventhostmodule.ui.screens.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast

import com.example.eventhostmodule.ui.components.LoginTextField
import com.example.eventhostmodule.ui.theme.*
import com.example.eventhostmodule.data.local.SharedPrefManager

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    defaultToPhone: Boolean = false
) {
    var isLoginMode by remember { mutableStateOf(true) }
    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isButtonPressed by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val buttonScale by animateFloatAsState(
        targetValue = if (isButtonPressed) 0.98f else 1f,
        animationSpec = tween(100),
        label = "button_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Icon
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(
                        LoginPrimary.copy(alpha = 0.1f),
                        RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Event,
                    contentDescription = null,
                    tint = LoginPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Title
            Text(
                if (isLoginMode) "Log In" else "Sign Up",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = LoginTextPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Subtitle
            Text(
                "Let's get started by setting up your profile and exploring your dashboard.",
                fontSize = 15.sp,
                color = LoginTextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Toggle
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(LoginInputBg, RoundedCornerShape(12.dp))
                    .padding(4.dp)
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .background(
                                if (isLoginMode) White else Color.Transparent,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { isLoginMode = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Log In",
                            color = if (isLoginMode) LoginPrimary else LoginTextTertiary)
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .background(
                                if (!isLoginMode) White else Color.Transparent,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { isLoginMode = false },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Sign Up",
                            color = if (!isLoginMode) LoginPrimary else LoginTextTertiary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Inputs
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                Column {
                    Text(
                        "Phone Number / Email",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LoginTextPrimary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LoginTextField(
                        value = emailOrPhone,
                        onValueChange = { emailOrPhone = it },
                        label = "",
                        placeholder = "Enter your phone or email",
                        leadingIcon = Icons.Default.Person,
                        keyboardType = KeyboardType.Email
                    )
                }

                Column {
                    Text(
                        "Password",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LoginTextPrimary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LoginTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "",
                        placeholder = "Password",
                        leadingIcon = Icons.Default.Lock,
                        trailingIcon = {
                            Icon(
                                imageVector = if (isPasswordVisible)
                                    Icons.Default.VisibilityOff
                                else Icons.Default.Visibility,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    isPasswordVisible = !isPasswordVisible
                                }
                            )
                        },
                        isPassword = true,
                        isPasswordVisible = isPasswordVisible
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Button
            Button(
                onClick = {
                    val prefManager = SharedPrefManager.getInstance(context)
                    if (emailOrPhone.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (isLoginMode) {
                        if (prefManager.validateLogin(emailOrPhone, password)) {
                            onLogin()
                        } else {
                            Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        prefManager.registerUser(emailOrPhone, password)
                        Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                        onLogin()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .scale(buttonScale),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(LoginPrimary)
            ) {
                Text(if (isLoginMode) "Login" else "Sign Up", fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}