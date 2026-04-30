package com.example.sabinacosmeticapplication.core.util

data class AppCategory(
    val rawKey: String,
    val displayName: String,
    val subtitle: String,
    val emoji: String
)

object AppCategoryCatalog {

    val all = listOf(
        AppCategory(
            rawKey = "skincare",
            displayName = "Skin Care",
            subtitle = "Daily care essentials",
            emoji = "🧴"
        ),
        AppCategory(
            rawKey = "serum",
            displayName = "Serum",
            subtitle = "Brightening & hydration",
            emoji = "💧"
        ),
        AppCategory(
            rawKey = "suncare",
            displayName = "Sun Care",
            subtitle = "SPF protection",
            emoji = "☀️"
        ),
        AppCategory(
            rawKey = "cream",
            displayName = "Cream",
            subtitle = "Barrier & moisture",
            emoji = "🫙"
        ),
        AppCategory(
            rawKey = "toner",
            displayName = "Toner",
            subtitle = "Fresh prep step",
            emoji = "✨"
        ),
        AppCategory(
            rawKey = "cleanser",
            displayName = "Cleanser",
            subtitle = "Gentle wash",
            emoji = "🫧"
        ),
        AppCategory(
            rawKey = "lipcare",
            displayName = "Lip Care",
            subtitle = "Soft lips",
            emoji = "💄"
        ),
        AppCategory(
            rawKey = "ampoule",
            displayName = "Ampoule",
            subtitle = "Concentrated care",
            emoji = "🩵"
        )
    )
}