package com.example.sabinacosmeticapplication

import androidx.room.Room
import com.example.sabinacosmeticapplication.data.local.cart.AppDatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sabinacosmeticapplication.navigation.MainNavHost
import com.example.sabinacosmeticapplication.ui.theme.SabinaCosmeticApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "sabina_cosmetic_db"
        ).build()

        setContent {
            SabinaCosmeticApplicationTheme {
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
fun SabinaAppRoot() {
    MainNavHost()
}

@Preview(showBackground = true)
@Composable
private fun SabinaAppPreview() {
    SabinaCosmeticApplicationTheme {
        SabinaAppRoot()
    }
}