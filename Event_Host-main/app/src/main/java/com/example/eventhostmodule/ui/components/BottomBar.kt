package com.example.eventhostmodule.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.eventhostmodule.ui.screens.host.BottomNavItem
import com.example.eventhostmodule.ui.theme.PrimaryOrange

@Composable
fun BottomBar(navController: NavController) {

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Chat,
        BottomNavItem.Wallet,
        BottomNavItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .shadow(12.dp, RoundedCornerShape(30.dp))
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            items.forEach { item ->

                val isSelected = currentRoute == item.route

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                            navController.navigate(item.route) {

                                // ✅ Prevent stacking
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {

                    // 🔥 Better highlight
                    val bgColor =
                        if (isSelected) PrimaryOrange.copy(alpha = 0.2f)
                        else Color.Transparent

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .background(bgColor, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (isSelected) PrimaryOrange else Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = item.label,
                        fontSize = 12.sp,
                        color = if (isSelected) PrimaryOrange else Color.Gray
                    )
                }
            }
        }
    }
}