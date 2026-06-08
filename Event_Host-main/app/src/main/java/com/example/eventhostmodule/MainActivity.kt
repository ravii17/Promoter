package com.example.eventhostmodule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.eventhostmodule.data.model.EventViewModel
import com.example.eventhostmodule.navigation.AppNavGraph
import com.example.eventhostmodule.ui.theme.EventHostModuleTheme

class MainActivity : ComponentActivity() {

    private val eventViewModel: EventViewModel by viewModels()  // ✅ Activity-scoped

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EventHostModuleTheme {
                App(eventViewModel = eventViewModel)    // ✅ pass down
            }
        }
    }
}

@Composable
fun App(eventViewModel: EventViewModel) {               // ✅ receive here
    val navController = rememberNavController()

    Surface(modifier = Modifier.fillMaxSize()) {
        AppNavGraph(
            navController = navController,
            eventViewModel = eventViewModel             // ✅ pass to NavGraph
        )
    }
}