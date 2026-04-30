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
import java.util.Locale

object CategoryIconResolver {

    fun resolve(iconName: String): ImageVector {
        val key = iconName
            .trim()
            .lowercase(Locale.US)
            .replace("_", "-")
            .replace(" ", "-")

        return when {
            key in listOf("female", "woman", "women", "women-care", "ayol") ->
                Icons.Outlined.Woman

            key in listOf("male", "man", "men", "men-care", "erkak") ->
                Icons.Outlined.Male

            key.contains("baby") || key.contains("child") || key.contains("kids") ->
                Icons.Outlined.BabyChangingStation

            key.contains("face") || key.contains("skin") || key.contains("yuz") ->
                Icons.Outlined.Face

            key.contains("hair") || key.contains("soch") || key.contains("shampoo") ||
                    key.contains("conditioner") || key.contains("scalp") ->
                Icons.Outlined.Spa

            key.contains("body") || key.contains("tana") ->
                Icons.Outlined.Accessibility

            key.contains("makeup") || key.contains("cosmetic") || key.contains("lip") ||
                    key.contains("eye") || key.contains("foundation") || key.contains("powder") ->
                Icons.Outlined.Brush

            key.contains("sun") || key.contains("spf") || key.contains("sunscreen") ->
                Icons.Outlined.WbSunny

            key.contains("vitamin") || key.contains("supplement") || key.contains("collagen") ||
                    key.contains("biotin") ->
                Icons.Outlined.Medication

            key.contains("digestive") || key.contains("probiotic") || key.contains("stomach") ->
                Icons.Outlined.HealthAndSafety

            key.contains("immune") || key.contains("health") || key.contains("wellness") ->
                Icons.Outlined.MonitorHeart

            key.contains("nail") || key.contains("tirnoq") ->
                Icons.Outlined.AutoAwesome

            key.contains("hand") || key.contains("qo'l") || key.contains("qol") ->
                Icons.Outlined.CleanHands

            key.contains("foot") || key.contains("oyoq") ->
                Icons.Outlined.Sanitizer

            key.contains("perfume") || key.contains("fragrance") || key.contains("scent") ->
                Icons.Outlined.LocalFlorist

            key.contains("sensitive") || key.contains("care") ->
                Icons.Outlined.FavoriteBorder

            else -> Icons.Outlined.Spa
        }
    }
}