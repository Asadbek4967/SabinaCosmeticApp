package com.example.sabinacosmeticapplication.core.util

import java.text.NumberFormat
import java.util.Locale

object PriceFormatter {

    private val formatter = NumberFormat.getNumberInstance(Locale.US)

    fun formatWon(amount: Int): String {
        return "₩${formatter.format(amount)}"
    }
}