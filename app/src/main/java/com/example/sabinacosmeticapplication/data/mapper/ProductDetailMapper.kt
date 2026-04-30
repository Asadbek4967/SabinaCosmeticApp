package com.example.sabinacosmeticapplication.data.mapper

import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.data.model.ProductVideo
import com.example.sabinacosmeticapplication.data.remote.dto.ProductDetailDto
import java.util.Locale
import kotlin.math.roundToInt

fun ProductDetailDto.toProduct(): Product {
    val safeId = id.orEmpty().trim().ifBlank { "unknown-product" }

    val safeTitle = localized?.title
        .orEmpty()
        .trim()
        .ifBlank { title.orEmpty().trim() }
        .ifBlank { "Unknown Product" }

    val safeBrand = brand.orEmpty().trim().ifBlank { "Unknown Brand" }

    val resolvedCategory = category?.nameEn?.trim()?.takeIf { it.isNotBlank() }
        ?: category?.nameUz?.trim()?.takeIf { it.isNotBlank() }
        ?: category?.nameRu?.trim()?.takeIf { it.isNotBlank() }
        ?: category?.nameKo?.trim()?.takeIf { it.isNotBlank() }
        ?: "Cosmetic"

    val resolvedDescription = localized?.description
        .orEmpty()
        .trim()
        .ifBlank { description.orEmpty().trim() }
        .ifBlank { shortDescription.orEmpty().trim() }
        .ifBlank { "$safeBrand $safeTitle is a carefully selected beauty product for daily use." }

    val currentPriceValue = price?.roundToInt()?.coerceAtLeast(0) ?: 0
    val oldPriceValue = oldPrice?.roundToInt()?.takeIf { it > 0 }

    val mainImage = resolveDetailMainImage(
        thumbnailUrl = thumbnailUrl,
        imageUrl = imageUrl
    )

    val galleryImages = buildList {
        if (mainImage.isNotBlank()) add(mainImage)

        images
            .orEmpty()
            .sortedBy { it.sortOrder ?: Int.MAX_VALUE }
            .mapNotNull { image ->
                image.imageUrl?.trim()?.takeIf { url -> url.isNotBlank() }
            }
            .forEach { url ->
                if (!contains(url)) add(url)
            }
    }

    val resolvedDiscountLabel = resolveDetailDiscountLabel(
        currentPriceValue = currentPriceValue,
        oldPriceValue = oldPriceValue
    )

    val mappedVideos = videos
        .orEmpty()
        .sortedBy { it.sortOrder ?: Int.MAX_VALUE }
        .mapNotNull { video ->
            val safeUrl = video.videoUrl.orEmpty().trim()

            if (safeUrl.isBlank()) {
                null
            } else {
                ProductVideo(
                    id = video.id.orEmpty(),
                    title = video.title.orEmpty().trim().ifBlank { "Product video" },
                    videoUrl = safeUrl,
                    thumbnailUrl = video.thumbnailUrl?.trim()?.takeIf { it.isNotBlank() }
                )
            }
        }

    return Product(
        id = safeId,
        title = safeTitle,
        brand = safeBrand,
        category = resolvedCategory,
        price = currentPriceValue.toWonText(),
        priceValue = currentPriceValue,
        oldPrice = oldPriceValue?.toWonText(),
        discountLabel = resolvedDiscountLabel,
        imageUrl = galleryImages.firstOrNull().orEmpty(),
        imageRes = null,
        description = resolvedDescription,
        isFlashSale = resolvedDiscountLabel != null,
        isBestSeller = isBestSeller == true || isFeatured == true,
        benefits = localized?.benefits?.trim()?.takeIf { it.isNotBlank() },
        howToUse = localized?.howToUse?.trim()?.takeIf { it.isNotBlank() },
        ingredients = localized?.ingredients?.trim()?.takeIf { it.isNotBlank() },
        warning = localized?.warning?.trim()?.takeIf { it.isNotBlank() },
        skinType = skinType?.trim()?.takeIf { it.isNotBlank() },
        galleryImages = galleryImages,
        videos = mappedVideos
    )
}

private fun resolveDetailMainImage(
    thumbnailUrl: String?,
    imageUrl: String?
): String {
    return listOf(thumbnailUrl, imageUrl)
        .firstOrNull { !it.isNullOrBlank() }
        ?.trim()
        .orEmpty()
}

private fun resolveDetailDiscountLabel(
    currentPriceValue: Int,
    oldPriceValue: Int?
): String? {
    if (
        oldPriceValue != null &&
        oldPriceValue > currentPriceValue &&
        currentPriceValue > 0
    ) {
        val percent = (((oldPriceValue - currentPriceValue).toDouble() / oldPriceValue) * 100)
            .roundToInt()

        return if (percent > 0) "$percent% OFF" else null
    }

    return null
}

private fun Int.toWonText(): String {
    return "₩" + "%,d".format(Locale.US, this)
}