package com.example.sabinacosmeticapplication.data.mapper

import com.example.sabinacosmeticapplication.core.util.PriceFormatter
import com.example.sabinacosmeticapplication.data.model.Product

fun Product.toUiProduct(): Product {
    return copy(
        title = safeTitle,
        brand = brand.trim(),
        category = safeCategory,
        price = formattedPrice,
        oldPrice = normalizeOldPrice(oldPrice),
        description = safeDescription,
        imageUrl = imageUrl.trim(),
        discountLabel = discountLabel?.trim()?.takeIf { it.isNotBlank() }
    )
}

private fun normalizeOldPrice(oldPrice: String?): String? {
    val value = oldPrice
        ?.filter { it.isDigit() }
        ?.toIntOrNull()

    return if (value != null && value > 0) {
        PriceFormatter.formatWon(value)
    } else {
        oldPrice?.trim()?.takeIf { it.isNotBlank() }
    }
}