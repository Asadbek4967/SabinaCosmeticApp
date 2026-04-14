package com.example.sabinacosmeticapplication.ui.components.category

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Accessibility
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BabyChangingStation
import androidx.compose.material.icons.outlined.Brush
import androidx.compose.material.icons.outlined.CleanHands
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.LocalFlorist
import androidx.compose.material.icons.outlined.Male
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.Sanitizer
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.Woman
import androidx.compose.ui.graphics.vector.ImageVector

object CategoryIconResolver {

    fun resolve(iconName: String): ImageVector {
        return when (iconName) {
            "female" -> Icons.Outlined.Woman
            "male" -> Icons.Outlined.Male
            "baby" -> Icons.Outlined.BabyChangingStation
            "face" -> Icons.Outlined.Face
            "hair" -> Icons.Outlined.Spa
            "body" -> Icons.Outlined.Accessibility
            "makeup" -> Icons.Outlined.Brush
            "sun" -> Icons.Outlined.WbSunny
            "vitamin" -> Icons.Outlined.Medication
            "digestive" -> Icons.Outlined.HealthAndSafety
            "immune" -> Icons.Outlined.MonitorHeart
            "nail" -> Icons.Outlined.AutoAwesome
            "hand" -> Icons.Outlined.CleanHands
            "foot" -> Icons.Outlined.Sanitizer
            "perfume" -> Icons.Outlined.LocalFlorist
            "sensitive" -> Icons.Outlined.FavoriteBorder
            else -> Icons.Outlined.Spa
        }
    }
}