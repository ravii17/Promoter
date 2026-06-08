package com.example.eventhostmodule.ui.screens.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventhostmodule.ui.components.OtpInputField
import com.example.eventhostmodule.ui.theme.OtpPrimary
import com.example.eventhostmodule.ui.theme.White

@Composable
fun OtpVerificationScreen(
    onBack: () -> Unit,
    onVerify: () -> Unit
) {
    var otp1 by remember { mutableStateOf("") }
    var otp2 by remember { mutableStateOf("") }
    var otp3 by remember { mutableStateOf("") }
    var otp4 by remember { mutableStateOf("") }
    var focusedIndex by remember { mutableIntStateOf(0) }
    var isButtonPressed by remember { mutableStateOf(false) }

    val focusRequesters = remember { List(4) { FocusRequester() } }

    val buttonScale by animateFloatAsState(
        targetValue = if (isButtonPressed) 0.98f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "button_scale"
    )

    val otpValues = listOf(otp1, otp2, otp3, otp4)
    val otpSetters = listOf(
        { value: String -> otp1 = value },
        { value: String -> otp2 = value },
        { value: String -> otp3 = value },
        { value: String -> otp4 = value }
    )

    val isOtpComplete = otp1.isNotEmpty() && otp2.isNotEmpty() && otp3.isNotEmpty() && otp4.isNotEmpty()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.clickable(onClick = onBack),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = OtpPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Back",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = OtpPrimary
                    )
                }
            }

            // Main Content - Centered
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Title
                Text(
                    text = "Enter Verification Code",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    letterSpacing = (-0.5).sp,
                    lineHeight = 38.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Subtitle
                Text(
                    text = "We sent a 4-digit code to your phone.",
                    fontSize = 17.sp,
                    color = Color.Gray,
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(48.dp))

                // OTP Input Fields
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(4) { index ->
                        Box(
                            modifier = Modifier.clickable {
                                focusedIndex = index
                                focusRequesters[index].requestFocus()
                            }
                        ) {
                            OtpInputField(
                                value = otpValues[index],
                                onValueChange = { value ->
                                    otpSetters[index](value)
                                    if (value.isNotEmpty() && index < 3) {
                                        focusedIndex = index + 1
                                        focusRequesters[index + 1].requestFocus()
                                    } else if (value.isEmpty() && index > 0) {
                                        focusedIndex = index - 1
                                        focusRequesters[index - 1].requestFocus()
                                    }
                                },
                                isFocused = focusedIndex == index,
                                focusRequester = focusRequesters[index]
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                // Resend OTP
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Didn't receive the code?",
                        fontSize = 15.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Resend OTP",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OtpPrimary,
                        modifier = Modifier.clickable { /* Handle resend */ }
                    )
                }
            }

            // Footer with Verify Button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 32.dp)
            ) {
                Button(
                    onClick = {
                        isButtonPressed = true
                        if (isOtpComplete) {
                            onVerify()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .scale(buttonScale),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isOtpComplete) OtpPrimary else Color.Gray.copy(alpha = 0.5f),
                        contentColor = White,
                        disabledContainerColor = Color.Gray.copy(alpha = 0.5f),
                        disabledContentColor = White.copy(alpha = 0.7f)
                    ),
                    enabled = isOtpComplete
                ) {
                    Text(
                        text = "Verify",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
