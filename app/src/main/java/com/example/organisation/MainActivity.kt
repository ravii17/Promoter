package com.example.organisation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PanUploadTheme {
                var frontImageUri by remember { mutableStateOf<Uri?>(null) }
                var backImageUri by remember { mutableStateOf<Uri?>(null) }

                val frontLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    frontImageUri = uri
                }

                val backLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    backImageUri = uri
                }

                PanUploadScreen(
                    frontImageUri = frontImageUri,
                    backImageUri = backImageUri,
                    onFrontUploadClick = { frontLauncher.launch("image/*") },
                    onBackUploadClick = { backLauncher.launch("image/*") },
                    onBackClick = { finish() },
                    onNextClick = {
                        val intent = Intent(this@MainActivity, AddPaymentActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun PanUploadTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFFF9A825),
            background = Color.White,
            surface = Color.White
        ),
        content = content
    )
}
