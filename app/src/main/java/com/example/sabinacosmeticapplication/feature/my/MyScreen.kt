package com.example.sabinacosmeticapplication.feature.my

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MyScreen(padding: PaddingValues) {
    Text("My", modifier = Modifier.padding(padding))
}