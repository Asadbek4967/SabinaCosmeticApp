package com.example.sabinacosmeticapplication.data.mapper

import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.data.model.ProductVideo
import com.example.sabinacosmeticapplication.data.remote.dto.ProductDetailDto
import kotlin.math.roundToInt

fun ProductDetailDto.toProduct(): Product {
    val safeId = id.orEmpty().trim().ifBlank { "unknown-product" }

    val safeTitle = localized?.title
        .orEmpty()
        .trim()
        .ifBlank { title.orEmpty().trim() }
        .ifBlank { "Unknown Product" }

    val safeBrand = brand.orEmpty().trim().ifBlank { "Unknown Brand" }

    val resolvedCategory = category?.nameEn
        ?.trim()
        ?.ifBlank { null }
        ?: category?.nameUz?.trim()?.ifBlank { null }
        ?: category?.nameRu?.trim()?.ifBlank { null }
        ?: category?.nameKo?.trim()?.ifBlank { null }
        ?: "Skincare"

    val resolvedDescription = localized?.description
        .orEmpty()
        .trim()
        .ifBlank { description.orEmpty().trim() }
        .ifBlank { shortDescription.orEmpty().trim() }
        .ifBlank {
            "$safeBrand $safeTitle is a carefully selected beauty product for daily use."
        }

    val currentPriceValue = price?.roundToInt()?.coerceAtLeast(0) ?: 0
    val oldPriceValue = oldPrice?.roundToInt()?.takeIf { it > 0 }

    val galleryImages = buildList {
        thumbnailUrl?.trim()?.takeIf { it.isNotBlank() }?.let { add(it) }
        imageUrl?.trim()?.takeIf { it.isNotBlank() && !contains(it) }?.let { add(it) }

        images
            .sortedBy { it.sortOrder ?: Int.MAX_VALUE }
            .mapNotNull { it.imageUrl?.trim()?.takeIf(String::isNotBlank) }
            .forEach { image ->
                if (!contains(image)) add(image)
            }
    }

    val resolvedImageUrl = galleryImages.firstOrNull().orEmpty()

    val resolvedDiscountLabel = if (
        oldPriceValue != null &&
        oldPriceValue > currentPriceValue &&
        currentPriceValue > 0
    ) {
        val percent = (((oldPriceValue - currentPriceValue).toDouble() / oldPriceValue) * 100)
            .roundToInt()
        if (percent > 0) "$percent% OFF" else null
    } else {
        null
    }

    val mappedVideos = videos
        .sortedBy { it.sortOrder ?: Int.MAX_VALUE }
        .mapNotNull { video ->
            val safeUrl = video.videoUrl.orEmpty().trim()
            if (safeUrl.isBlank()) {
                null
            } else {
                ProductVideo(
                    id = video.id.orEmpty(),
                    title = video.title.orEmpty().ifBlank { "Product video" },
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
        price = "₩" + "%,d".format(currentPriceValue),
        priceValue = currentPriceValue,
        oldPrice = oldPriceValue?.let { "₩" + "%,d".format(it) },
        discountLabel = resolvedDiscountLabel,
        imageUrl = resolvedImageUrl,
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
        videos = mappedVideos,
    )
}