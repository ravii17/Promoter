package com.example.eventhostmodule.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.eventhostmodule.navigation.Screen
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
@Composable
fun LoaderScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        delay(1500) // show loader for 1.5 sec
        navController.navigate(Screen.Splash.route) {
            popUpTo(Screen.Loader.route) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color(0xFFFF7A00), CircleShape)
        )
    }
}