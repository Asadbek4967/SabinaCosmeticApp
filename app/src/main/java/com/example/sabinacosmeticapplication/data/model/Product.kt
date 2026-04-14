package com.example.sabinacosmeticapplication.data.model

import androidx.annotation.DrawableRes
import com.example.sabinacosmeticapplication.core.util.CategoryNormalizer
import com.example.sabinacosmeticapplication.core.util.PriceFormatter

data class Product(
    val id: String,
    val title: String,
    val brand: String,
    val category: String,
    val price: String,
    val priceValue: Int,
    val oldPrice: String? = null,
    val discountLabel: String? = null,
    val imageUrl: String = "",
    @DrawableRes val imageRes: Int? = null,
    val description: String,
    val isFlashSale: Boolean = false,
    val isBestSeller: Boolean = false
) {
    val safeId: String
        get() = id.trim()

    val safeTitle: String
        get() = title.trim().ifBlank { "Unknown Product" }

    val safeBrand: String
        get() = brand.trim().ifBlank { "Unknown Brand" }

    val safeDescription: String
        get() = description.trim().ifBlank { "No description available." }

    val normalizedCategory: String
        get() = CategoryNormalizer.normalize(category)

    val safeCategory: String
        get() = normalizedCategory.ifBlank { "General" }

    val formattedPrice: String
        get() = if (priceValue > 0) {
            PriceFormatter.formatWon(priceValue)
        } else {
            price.trim().ifBlank { PriceFormatter.formatWon(0) }
        }

    val formattedOldPrice: String?
        get() = oldPrice
            ?.trim()
            ?.takeIf { it.isNotBlank() }

    val hasDiscount: Boolean
        get() = !discountLabel.isNullOrBlank()

    val normalizedImageUrl: String
        get() = imageUrl
            .trim()
            .replace("\\", "/")
            .removeSurrounding("\"")
            .removeSurrounding("'")

    val hasRemoteImage: Boolean
        get() = normalizedImageUrl.isNotBlank()

    val hasLocalImage: Boolean
        get() = imageRes != null

    val hasAnyImage: Boolean
        get() = hasRemoteImage || hasLocalImage

    val resolvedImageUrl: String?
        get() = normalizedImageUrl.takeIf { it.isNotBlank() }

    val shouldShowImageFallback: Boolean
        get() = !hasAnyImage

    val isFeaturedProduct: Boolean
        get() = isBestSeller || isFlashSale || hasDiscount

    val primaryBadge: String?
        get() = when {
            isFlashSale -> "Flash Sale"
            isBestSeller -> "Best Seller"
            hasDiscount -> discountLabel?.trim()?.takeIf { it.isNotBlank() }
            else -> null
        }

    val secondaryBadge: String?
        get() = when {
            isFlashSale && hasDiscount -> discountLabel?.trim()?.takeIf { it.isNotBlank() }
            else -> null
        }

    val searchableText: String
        get() = listOf(
            safeTitle,
            safeBrand,
            safeCategory,
            safeDescription
        ).joinToString(" ")

    val isValid: Boolean
        get() = safeId.isNotBlank() && safeTitle.isNotBlank()
}