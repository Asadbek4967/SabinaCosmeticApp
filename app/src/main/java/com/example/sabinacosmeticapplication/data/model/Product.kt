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
    val isBestSeller: Boolean = false,

    // Detail data
    val benefits: String? = null,
    val howToUse: String? = null,
    val ingredients: String? = null,
    val warning: String? = null,
    val skinType: String? = null,
    val galleryImages: List<String> = emptyList(),
    val videos: List<ProductVideo> = emptyList(),
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

    val normalizedGalleryImages: List<String>
        get() = buildList {
            normalizedImageUrl.takeIf { it.isNotBlank() }?.let { add(it) }

            galleryImages
                .map { galleryUrl ->
                    galleryUrl
                        .trim()
                        .replace("\\", "/")
                        .removeSurrounding("\"")
                        .removeSurrounding("'")
                }
                .filter { it.isNotBlank() }
                .forEach { safeUrl ->
                    if (!contains(safeUrl)) add(safeUrl)
                }
        }

    val primaryImageUrl: String?
        get() = normalizedGalleryImages.firstOrNull()

    val hasRemoteImage: Boolean
        get() = !primaryImageUrl.isNullOrBlank()

    val hasLocalImage: Boolean
        get() = imageRes != null

    val hasAnyImage: Boolean
        get() = hasRemoteImage || hasLocalImage

    val resolvedImageUrl: String?
        get() = primaryImageUrl

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

    val safeBenefits: String?
        get() = benefits?.trim()?.takeIf { it.isNotBlank() }

    val safeHowToUse: String?
        get() = howToUse?.trim()?.takeIf { it.isNotBlank() }

    val safeIngredients: String?
        get() = ingredients?.trim()?.takeIf { it.isNotBlank() }

    val safeWarning: String?
        get() = warning?.trim()?.takeIf { it.isNotBlank() }

    val safeSkinType: String?
        get() = skinType?.trim()?.takeIf { it.isNotBlank() }

    val hasExtendedDetail: Boolean
        get() = safeBenefits != null ||
                safeHowToUse != null ||
                safeIngredients != null ||
                safeWarning != null ||
                safeSkinType != null ||
                videos.isNotEmpty()

    val searchableText: String
        get() = listOfNotNull(
            safeTitle,
            safeBrand,
            safeCategory,
            safeDescription,
            safeBenefits,
            safeHowToUse,
            safeIngredients,
            safeWarning,
            safeSkinType
        ).joinToString(" ")

    val isValid: Boolean
        get() = safeId.isNotBlank() && safeTitle.isNotBlank()
}

data class ProductVideo(
    val id: String,
    val title: String,
    val videoUrl: String,
    val thumbnailUrl: String? = null,
) {
    val safeId: String
        get() = id.trim()

    val safeTitle: String
        get() = title.trim().ifBlank { "Product video" }

    val safeVideoUrl: String
        get() = videoUrl
            .trim()
            .replace("\\", "/")
            .removeSurrounding("\"")
            .removeSurrounding("'")

    val safeThumbnailUrl: String?
        get() = thumbnailUrl
            ?.trim()
            ?.replace("\\", "/")
            ?.removeSurrounding("\"")
            ?.removeSurrounding("'")
            ?.takeIf { it.isNotBlank() }

    val isValid: Boolean
        get() = safeVideoUrl.isNotBlank()
}