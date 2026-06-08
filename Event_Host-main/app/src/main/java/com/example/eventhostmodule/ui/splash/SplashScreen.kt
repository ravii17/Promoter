package com.example.eventhostmodule.ui.splash

import com.example.eventhostmodule.R
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.eventhostmodule.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    var showBackground by remember { mutableStateOf(false) }
    var shrinkLogo by remember { mutableStateOf(false) }

    val logoScale by animateFloatAsState(
        targetValue = if (shrinkLogo) 0.88f else 1f,
        animationSpec = tween(700, easing = EaseInOut),
        label = "logo_scale"
    )

    val logoOpacity by animateFloatAsState(
        targetValue = if (shrinkLogo) 0.85f else 1f,
        animationSpec = tween(700, easing = EaseInOut),
        label = "logo_opacity"
    )

    val backgroundOpacity by animateFloatAsState(
        targetValue = if (showBackground) 1f else 0f,
        animationSpec = tween(1200, easing = EaseInOut),
        label = "background_opacity"
    )

    Box(modifier = Modifier.fillMaxSize()) {

        // 🔶 Base orange color
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD95727))
        )

        // 🖼 Background image (fade)
        Image(
            painter = painterResource(id = R.drawable.splash_bg), // IMPORTANT
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(backgroundOpacity),
            contentScale = ContentScale.Crop
        )

        // 🔥 Logo
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_splash), // IMPORTANT
                contentDescription = null,
                modifier = Modifier
                    .height(120.dp)
                    .scale(logoScale)
                    .alpha(logoOpacity)
            )
        }
    }

    // ⏱ Animation timing
    LaunchedEffect(Unit) {
        delay(1000)
        showBackground = true

        delay(1200)
        shrinkLogo = true

        delay(1000)

        navController.navigate(Screen.Onboarding1.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }
}