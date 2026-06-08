package com.example.eventhostmodule.ui.screens.host

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.eventhostmodule.navigation.Screen

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : BottomNavItem(
        Screen.HostHome.route,
        Icons.Default.Home,
        "Home"
    )

    object Wallet : BottomNavItem(
        Screen.Wallet.route,
        Icons.Default.AccountBalanceWallet,
        "Wallet"
    )

    object Chat : BottomNavItem(
        Screen.Chat.route,
        Icons.Default.Chat,
        "Chat"
    )

    object Profile : BottomNavItem(
        Screen.Profile.route,
        Icons.Default.Person,
        "Profile"
    )
}