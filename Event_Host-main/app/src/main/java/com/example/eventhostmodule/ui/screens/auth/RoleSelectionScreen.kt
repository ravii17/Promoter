package com.example.eventhostmodule.ui.screens.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.eventhostmodule.ui.theme.RoleBackgroundLight
import com.example.eventhostmodule.ui.theme.RoleOrangeButton
import com.example.eventhostmodule.ui.theme.RolePrimary
import com.example.eventhostmodule.ui.theme.RoleSurfaceLight
import com.example.eventhostmodule.ui.theme.White
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.graphics.vector.ImageVector

enum class UserRole {
    ORGANIZER,
    CREW,
    EVENT_HOST

}

@Composable
fun RoleSelectionScreen(
    onContinue: (UserRole) -> Unit,
    onLogin: () -> Unit
) {
    var selectedRole by remember { mutableStateOf<UserRole?>(null) }
    var isButtonPressed by remember { mutableStateOf(false) }

    val buttonScale by animateFloatAsState(
        targetValue = if (isButtonPressed) 0.98f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "button_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RoleBackgroundLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .padding(bottom = 120.dp)

        ) {
            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "How will you use the app?",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    letterSpacing = (-0.5).sp,
                    lineHeight = 38.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Select your role to customize your experience.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }

            // Spacer(modifier = Modifier.weight(1f))

            // Role Cards
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)


            ) {
                // Event Organizer Card
                RoleCard(
                    title = "Event Organizer",
                    description = "I manage events, coordinate schedules, and oversee staff operations.",
                    icon = Icons.Default.Person,
                    imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAuWKYHN_Y6TEGfn81DW7azWoYtOuk8Yct8eQPq_0bstdjnQDHrIi_aEpBqKJuZId58TpmdsVWkBRDICBLuWz4IFLriHSeEh88Kz7V5K5AFGPmzlshS9XKWcBDFBQAVYR8n7Qj1avUB1n7q6-F41_vhBUyU24kqmWYf4m7Vmtp9hZBSUFJagf6f8hnQEp6B0UrB7Ph6doHuLdTG8IUiqO840HQ2huhC1N0dqMOMUwrMrLewd8CjNpxhEFNY5iOWG7r4OniNcF-QXiz_",
                    isSelected = selectedRole == UserRole.ORGANIZER,
                    onClick = {
                        selectedRole = UserRole.ORGANIZER
                    }
                )

                // Event Crew Card
                RoleCard(
                    title = "Event Crew",
                    description = "I work at events, need to check my shifts, and communicate with the team.",
                    icon = Icons.Default.Person,
                    imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDzzUTANRMsUL3y-cTpUnGW-7N_r8y0HBti60Upc-9pAP3DaH4-9-_5AyA5GAyorvaI5fF6Q9XB7Xye11jSCSnPalvnHIo5OOztT5ahFI-0NUoME3pmXZChnRR8RQ4zVc0HmZ8VWn9Rb3BTI_qt5f-4t7SZUXf21vWG32BjJSBWuosuLVtIavRxk2YdEAcEesJ4luAg5BtwBI-Hz7MIbO9BiPdE98Et5vBh_ohMNK2T0oiWwPtx3EfVO81AqOLq2f_VgsRumS2Dt22H",
                    isSelected = selectedRole == UserRole.CREW,
                    onClick = {
                        selectedRole = UserRole.CREW
                    },
                    iconBgColor = Color(0xFFF1F5F9)
                )
                // Event Host Card
                RoleCard(
                    title = "Host an Event",
                    description = "I want to create and manage my own events.",
                    icon = Icons.Default.Person,
                    imageUrl = "https://images.unsplash.com/photo-1511795409834-ef04bbd61622",
                    isSelected = selectedRole == UserRole.EVENT_HOST,
                    onClick = {
                        selectedRole = UserRole.EVENT_HOST
                    }
                )
            }

            // Spacer(modifier = Modifier.weight(1f))
        }

        // Bottom Fixed Footer
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    color = RoleBackgroundLight.copy(alpha = 0.95f)
                )
                .padding(16.dp, 16.dp, 16.dp, 32.dp)
        ) {
            // Continue Button
            Button(
                onClick = {
                    isButtonPressed = true
                    selectedRole?.let { onContinue(it) }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .scale(buttonScale),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedRole != null) RoleOrangeButton else Color.Gray.copy(alpha = 0.5f),
                    contentColor = White,
                    disabledContainerColor = Color.Gray.copy(alpha = 0.5f),
                    disabledContentColor = White.copy(alpha = 0.7f)
                ),
                enabled = selectedRole != null
            ) {
                Text(
                    text = "Continue",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Continue",
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Login Link
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account? ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
                Text(
                    text = "Log in",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF334155),
                    modifier = Modifier.clickable(onClick = onLogin)
                )
            }
        }
    }
}

@Composable
fun RoleCard(
    title: String,
    description: String,
    icon: ImageVector,
    imageUrl: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    iconBgColor: Color = RolePrimary.copy(alpha = 0.1f)
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressedState by interactionSource.collectIsPressedAsState()

    // Animated values
    val cardScale by animateFloatAsState(
        targetValue = if (isPressedState) 0.97f else 1f,
        animationSpec = spring(
            dampingRatio = 0.6f,
            stiffness = 300f
        ),
        label = "card_scale"
    )

    val borderWidth by animateFloatAsState(
        targetValue = if (isSelected) 2.5f else 0f,
        animationSpec = spring(
            dampingRatio = 0.7f,
            stiffness = 400f
        ),
        label = "border_width"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) RolePrimary else Color.Transparent,
        animationSpec = tween(durationMillis = 300),
        label = "border_color"
    )

    val shadowElevation by animateFloatAsState(
        targetValue = if (isSelected) 8f else if (isPressedState) 4f else 2f,
        animationSpec = spring(
            dampingRatio = 0.8f,
            stiffness = 500f
        ),
        label = "shadow_elevation"
    )

    val checkmarkScale by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = spring(
            dampingRatio = 0.6f,
            stiffness = 400f
        ),
        label = "checkmark_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .shadow(
                elevation = shadowElevation.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = if (isSelected) RolePrimary.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.1f)
            )
            .border(
                width = borderWidth.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = RoleSurfaceLight,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Column {
            // Image Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.05f))
                            )
                        )
                )

                // Selection Indicator with animation
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .scale(checkmarkScale)
                ) {
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(
                                    color = RolePrimary,
                                    shape = CircleShape
                                )
                                .shadow(
                                    elevation = 4.dp,
                                    shape = CircleShape,
                                    spotColor = RolePrimary.copy(alpha = 0.5f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(
                                    color = White.copy(alpha = 0.9f),
                                    shape = CircleShape
                                )
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFFE2E8F0),
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }

            // Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                color = iconBgColor,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = title,
                            tint = if (iconBgColor == Color(0xFFF1F5F9)) Color(0xFF475569) else RolePrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        letterSpacing = (-0.3).sp
                    )
                }

                Text(
                    text = description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(start = 48.dp)
                )
            }
        }
    }
}
