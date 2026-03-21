package com.example.sabinacosmeticapplication.ui.components

fun productCategoryEmoji(category: String): String {
    return when (category.trim().lowercase()) {
        "serum" -> "💧"
        "sun care" -> "☀️"
        "cream" -> "🫙"
        "cleanser" -> "🫧"
        "lip care" -> "💄"
        "ampoule" -> "🩵"
        "toner" -> "✨"
        else -> "🧴"
    }
}