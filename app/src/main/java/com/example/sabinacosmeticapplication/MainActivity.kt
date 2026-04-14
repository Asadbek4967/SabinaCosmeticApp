package com.example.sabinacosmeticapplication

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.example.sabinacosmeticapplication.core.navigation.AppNavGraph
import com.example.sabinacosmeticapplication.ui.theme.SabinaCosmeticApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SabinaCosmeticApplicationTheme {
                ConfigureSystemBars()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SabinaAppRoot()
                }
            }
        }
    }
}

@Composable
private fun ConfigureSystemBars() {
    val view = LocalView.current
    val darkTheme = isSystemInDarkTheme()
    val backgroundColor = MaterialTheme.colorScheme.background.toArgb()

    SideEffect {
        val window = (view.context as? Activity)?.window ?: return@SideEffect

        window.statusBarColor = backgroundColor
        window.navigationBarColor = backgroundColor

        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = !darkTheme
            isAppearanceLightNavigationBars = !darkTheme
        }
    }
}

@Composable
fun SabinaAppRoot() {
    val navController = rememberNavController()

    AppNavGraph(
        navController = navController
    )
}