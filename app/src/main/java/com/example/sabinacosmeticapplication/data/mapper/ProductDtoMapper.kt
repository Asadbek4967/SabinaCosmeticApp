package com.example.sabinacosmeticapplication.data.mapper

import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.data.remote.dto.ProductDto
import java.util.Locale

fun ProductDto.toProduct(): Product {
    val safePrice = price ?: 0.0

    val resolvedTitle = title?.takeIf { it.isNotBlank() }
        ?: name?.takeIf { it.isNotBlank() }
        ?: "Unknown Product"

    val resolvedImageUrl = imageUrl?.takeIf { it.isNotBlank() }
        ?: image?.takeIf { it.isNotBlank() }
        ?: ""

    return Product(
        id = id.orEmpty(),
        title = resolvedTitle,
        brand = brand?.takeIf { it.isNotBlank() } ?: "Unknown Brand",
        category = category?.takeIf { it.isNotBlank() } ?: "Other",
        price = "$" + String.format(Locale.US, "%.2f", safePrice),
        priceValue = safePrice.toInt(),
        oldPrice = null,
        discountLabel = null,
        imageUrl = resolvedImageUrl,
        imageRes = null,
        description = description ?: "",
        isFlashSale = false,
        isBestSeller = false
    )
}