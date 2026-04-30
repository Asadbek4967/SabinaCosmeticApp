package com.example.sabinacosmeticapplication.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

object AppShapes {

    // ===== Core radius scale =====
    val ExtraSmall = RoundedCornerShape(6.dp)
    val Small = RoundedCornerShape(10.dp)
    val Medium = RoundedCornerShape(14.dp)
    val Large = RoundedCornerShape(18.dp)
    val ExtraLarge = RoundedCornerShape(24.dp)

    // ===== Specialized shapes =====
    val Card = RoundedCornerShape(20.dp)
    val Dialog = RoundedCornerShape(24.dp)
    val BottomSheet = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    val TextField = RoundedCornerShape(16.dp)
    val Button = RoundedCornerShape(16.dp)
    val Badge = RoundedCornerShape(12.dp)
    val Pill = RoundedCornerShape(100.dp)
    val Full = RoundedCornerShape(999.dp)

    // ===== Material integration =====
    val Material = Shapes(
        extraSmall = ExtraSmall,
        small = Small,
        medium = Medium,
        large = Large,
        extraLarge = ExtraLarge
    )
}