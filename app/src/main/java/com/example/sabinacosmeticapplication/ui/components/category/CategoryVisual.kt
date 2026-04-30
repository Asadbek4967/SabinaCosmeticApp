package com.example.sabinacosmeticapplication.ui.components.category

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BabyChangingStation
import androidx.compose.material.icons.outlined.Brush
import androidx.compose.material.icons.outlined.CleanHands
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalFlorist
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.Male
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.material.icons.outlined.Sanitizer
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.Woman
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import java.util.Locale

data class CategoryVisual(
    val icon: ImageVector,
    val backgroundBrush: Brush,
    val backgroundColor: Color,
    val contentColor: Color,
)

object CategoryVisualResolver {

    fun resolve(
        iconName: String,
        title: String = "",
    ): CategoryVisual {
        val key = "$iconName $title"
            .trim()
            .lowercase(Locale.US)
            .replace("_", "-")
            .replace(" ", "-")

        return when {
            key.hasAny("offer", "discount", "sale", "coupon", "flash", "할인") ->
                visual(Icons.Outlined.LocalOffer, Color(0xFFFFF1F2), Color(0xFFFFDDE4), Color(0xFFFF3B5C))

            key.hasAny("rocket", "delivery", "shipping", "배송") ->
                visual(Icons.Outlined.RocketLaunch, Color(0xFFEAF2FF), Color(0xFFD9E8FF), Color(0xFF247CFF))

            key.hasAny("cleanser", "cleansing", "yuvish", "wash") ->
                visual(Icons.Outlined.CleanHands, Color(0xFFE0F2FE), Color(0xFFBAE6FD), Color(0xFF0284C7))

            key.hasAny("toner") ->
                visual(Icons.Outlined.WaterDrop, Color(0xFFEFF6FF), Color(0xFFDBEAFE), Color(0xFF2563EB))

            key.hasAny("serum", "ampoule", "ampula", "essence") ->
                visual(Icons.Outlined.Science, Color(0xFFF0FDFA), Color(0xFFCCFBF1), Color(0xFF0D9488))

            key.hasAny("sunscreen", "spf", "sun", "uv", "quyosh") ->
                visual(Icons.Outlined.WbSunny, Color(0xFFFFF7ED), Color(0xFFFFE8CC), Color(0xFFF97316))

            key.hasAny("mask", "pack", "maska") ->
                visual(Icons.Outlined.Face, Color(0xFFF5F3FF), Color(0xFFEDE9FE), Color(0xFF7C3AED))

            key.hasAny("hair", "soch", "shampoo", "shampun", "conditioner", "konditsioner", "scalp", "treatment", "treat") ->
                visual(Icons.Outlined.Spa, Color(0xFFEFFDF5), Color(0xFFD8F7E5), Color(0xFF16A34A))

            key.hasAny("body", "tana", "body-lotion", "lotion") ->
                visual(Icons.Outlined.FavoriteBorder, Color(0xFFF5F3FF), Color(0xFFEDE9FE), Color(0xFF7C3AED))

            key.hasAny("cream", "krem", "moisturizer") ->
                visual(Icons.Outlined.Spa, Color(0xFFFFF7ED), Color(0xFFFFEDD5), Color(0xFFF97316))

            key.hasAny("makeup", "makiyaj", "lip", "eye", "foundation", "powder", "cushion", "kushon", "ton-krem", "lab", "boyoq", "bo'yoq") ->
                visual(Icons.Outlined.Brush, Color(0xFFFFF1F2), Color(0xFFFFDDE4), Color(0xFFE11D48))

            key.hasAny("face", "skin", "yuz", "teri", "skincare") ->
                visual(Icons.Outlined.Face, Color(0xFFEFF6FF), Color(0xFFDCEBFF), Color(0xFF2563EB))

            key.hasAny("female", "woman", "women", "ayol") ->
                visual(Icons.Outlined.Woman, Color(0xFFFFEEF7), Color(0xFFFFD9EC), Color(0xFFE84393))

            key.hasAny("male", "man", "men", "erkak") ->
                visual(Icons.Outlined.Male, Color(0xFFEAF2FF), Color(0xFFDCEBFF), Color(0xFF2563EB))

            key.hasAny("baby", "child", "kids", "infant") ->
                visual(Icons.Outlined.BabyChangingStation, Color(0xFFFFF7E6), Color(0xFFFFE8B8), Color(0xFFF59E0B))

            key.hasAny("vitamin", "supplement", "collagen", "biotin") ->
                visual(Icons.Outlined.Medication, Color(0xFFF0FDF4), Color(0xFFDDFBE7), Color(0xFF15803D))

            key.hasAny("digestive", "probiotic", "stomach") ->
                visual(Icons.Outlined.HealthAndSafety, Color(0xFFECFDF5), Color(0xFFD8F8E8), Color(0xFF059669))

            key.hasAny("immune", "health", "wellness") ->
                visual(Icons.Outlined.MonitorHeart, Color(0xFFEFF6FF), Color(0xFFDCEBFF), Color(0xFF0284C7))

            key.hasAny("nail", "tirnoq") ->
                visual(Icons.Outlined.AutoAwesome, Color(0xFFFAF5FF), Color(0xFFF3E8FF), Color(0xFFA855F7))

            key.hasAny("hand", "qol", "qo'l") ->
                visual(Icons.Outlined.CleanHands, Color(0xFFF0FDFA), Color(0xFFDDFBF5), Color(0xFF0D9488))

            key.hasAny("foot", "oyoq") ->
                visual(Icons.Outlined.Sanitizer, Color(0xFFF8FAFC), Color(0xFFE2E8F0), Color(0xFF475569))

            key.hasAny("perfume", "fragrance", "scent", "parfum") ->
                visual(Icons.Outlined.LocalFlorist, Color(0xFFFFF1F2), Color(0xFFFFDDE4), Color(0xFFDB2777))

            else ->
                visual(Icons.Outlined.Home, Color(0xFFF8FAFC), Color(0xFFE2E8F0), Color(0xFF334155))
        }
    }

    private fun visual(
        icon: ImageVector,
        start: Color,
        end: Color,
        tint: Color,
    ): CategoryVisual {
        return CategoryVisual(
            icon = icon,
            backgroundBrush = Brush.linearGradient(listOf(start, end)),
            backgroundColor = start,
            contentColor = tint
        )
    }

    private fun String.hasAny(vararg keywords: String): Boolean {
        return keywords.any { contains(it) }
    }
}