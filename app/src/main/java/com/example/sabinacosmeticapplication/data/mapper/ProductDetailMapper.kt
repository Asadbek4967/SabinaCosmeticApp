package com.example.sabinacosmeticapplication.data.mapper

import com.example.sabinacosmeticapplication.data.model.Product
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
        .ifBlank {
            "$safeBrand $safeTitle is a carefully selected beauty product for daily use."
        }

    val currentPriceValue = price?.roundToInt()?.coerceAtLeast(0) ?: 0
    val oldPriceValue = oldPrice?.roundToInt()?.takeIf { it > 0 }

    val resolvedImageUrl = listOf(
        thumbnailUrl,
        imageUrl,
        images.orEmpty().firstOrNull { it.isThumbnail == true }?.imageUrl,
        images.orEmpty().firstOrNull()?.imageUrl,
    ).map { it.orEmpty().trim() }
        .firstOrNull { it.isNotBlank() }
        .orEmpty()

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
        description = buildDetailDescription(
            description = resolvedDescription,
            benefits = localized?.benefits,
            howToUse = localized?.howToUse,
            ingredients = localized?.ingredients,
            warning = localized?.warning,
            skinType = skinType,
        ),
        isFlashSale = resolvedDiscountLabel != null,
        isBestSeller = isBestSeller == true || isFeatured == true,
    )
}

private fun buildDetailDescription(
    description: String,
    benefits: String?,
    howToUse: String?,
    ingredients: String?,
    warning: String?,
    skinType: String?,
): String {
    val sections = buildList {
        add(description)

        skinType?.trim()?.takeIf { it.isNotBlank() }?.let {
            add("Skin type: $it")
        }

        benefits?.trim()?.takeIf { it.isNotBlank() }?.let {
            add("Benefits: $it")
        }

        howToUse?.trim()?.takeIf { it.isNotBlank() }?.let {
            add("How to use: $it")
        }

        ingredients?.trim()?.takeIf { it.isNotBlank() }?.let {
            add("Ingredients: $it")
        }

        warning?.trim()?.takeIf { it.isNotBlank() }?.let {
            add("Warning: $it")
        }
    }

    return sections.joinToString(separator = "\n\n")
}