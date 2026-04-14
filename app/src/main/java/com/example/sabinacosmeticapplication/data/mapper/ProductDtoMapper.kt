package com.example.sabinacosmeticapplication.data.mapper

import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.data.remote.dto.ProductDto
import java.util.Locale
import kotlin.math.roundToInt

private const val IMAGE_BASE_URL = ""

fun ProductDto.toProduct(): Product {
    val safeId = resolveProductId(
        id = id,
        slug = slug,
        title = title,
    )

    val safeTitle = title.sanitizeText().ifBlank { "Unknown Product" }
    val safeBrand = brand.sanitizeText().ifBlank { "Unknown Brand" }

    val resolvedCategory = resolveCategory(
        categoryId = categoryId,
        title = title,
        brand = brand,
        slug = slug,
        description = description ?: shortDescription,
    )

    val resolvedDescription = description
        .sanitizeText()
        .ifBlank {
            shortDescription.sanitizeText().ifBlank {
                "$safeBrand $safeTitle is a curated $resolvedCategory product designed for a smooth daily beauty routine."
            }
        }

    val currentPriceValue = parsePriceToInt(price)
    val oldPriceValue = parsePriceToIntOrNull(oldPrice)

    val resolvedImageUrl = resolveAbsoluteImageUrl(
        thumbnailUrl = thumbnailUrl,
        imageUrl = imageUrl,
    )

    val resolvedDiscountLabel = buildDiscountLabel(
        priceValue = currentPriceValue,
        oldPriceValue = oldPriceValue,
    )

    return Product(
        id = safeId,
        title = safeTitle,
        brand = safeBrand,
        category = resolvedCategory,
        price = currentPriceValue.formatCurrency(),
        priceValue = currentPriceValue,
        oldPrice = oldPriceValue?.formatCurrency(),
        discountLabel = resolvedDiscountLabel,
        imageUrl = resolvedImageUrl,
        imageRes = null,
        description = resolvedDescription,
        isFlashSale = resolvedDiscountLabel != null,
        isBestSeller = isBestSeller == true || isFeatured == true,
    )
}

private fun resolveProductId(
    id: String?,
    slug: String?,
    title: String?,
): String {
    val rawId = id.sanitizeText()
    if (rawId.isNotBlank()) return rawId

    val rawSlug = slug.sanitizeText()
    if (rawSlug.isNotBlank()) return rawSlug

    val fallbackTitle = title.sanitizeText()
        .lowercase(Locale.ROOT)
        .replace("[^a-z0-9]+".toRegex(), "-")
        .trim('-')

    return fallbackTitle.ifBlank { "unknown-product" }
}

private fun String?.sanitizeText(): String {
    return this.orEmpty().trim()
}

private fun parsePriceToInt(value: Double?): Int {
    return value?.roundToInt()?.coerceAtLeast(0) ?: 0
}

private fun parsePriceToIntOrNull(value: Double?): Int? {
    return value?.roundToInt()?.takeIf { it > 0 }
}

private fun resolveAbsoluteImageUrl(
    thumbnailUrl: String?,
    imageUrl: String?,
): String {
    val candidate = thumbnailUrl.sanitizeText().ifBlank { imageUrl.sanitizeText() }
    val raw = candidate.replace("\\", "/")

    if (raw.isBlank()) return ""
    if (raw.equals("null", ignoreCase = true)) return ""
    if (raw.equals("undefined", ignoreCase = true)) return ""

    return when {
        raw.startsWith("http://", ignoreCase = true) -> raw
        raw.startsWith("https://", ignoreCase = true) -> raw
        raw.startsWith("file://", ignoreCase = true) -> raw
        raw.startsWith("content://", ignoreCase = true) -> raw
        raw.startsWith("//") -> "https:$raw"
        raw.startsWith("/") -> {
            if (IMAGE_BASE_URL.isBlank()) raw else IMAGE_BASE_URL + raw
        }
        else -> {
            if (IMAGE_BASE_URL.isBlank()) raw else "$IMAGE_BASE_URL/$raw"
        }
    }
}

private fun buildDiscountLabel(
    priceValue: Int,
    oldPriceValue: Int?,
): String? {
    if (oldPriceValue == null || oldPriceValue <= 0) return null
    if (priceValue <= 0) return null
    if (oldPriceValue <= priceValue) return null

    val discountPercent =
        (((oldPriceValue - priceValue).toDouble() / oldPriceValue.toDouble()) * 100.0).roundToInt()

    return if (discountPercent > 0) "$discountPercent% OFF" else null
}

private fun resolveCategory(
    categoryId: String?,
    title: String?,
    brand: String?,
    slug: String?,
    description: String?,
): String {
    val source = normalizeForCategoryDetection(
        listOf(
            categoryId.orEmpty(),
            title.orEmpty(),
            brand.orEmpty(),
            slug.orEmpty(),
            description.orEmpty(),
        ).joinToString(" ")
    )

    return when {
        containsAny(source, "shampoo", "conditioner", "hair", "scalp") -> "Hair Care"
        containsAny(source, "body wash", "body lotion", "hand cream", "foot cream", "perfume", "fragrance") -> "Body Care"
        containsAny(source, "lipstick", "lip tint", "foundation", "concealer", "powder", "mascara", "eyeliner", "makeup", "cushion") -> "Makeup"
        containsAny(source, "sun stick", "sun cream", "sunscreen", "sunblock", "spf") -> "Sunscreen"
        containsAny(source, "sheet mask", "sleeping mask", "mask pack", "face mask") -> "Mask Pack"
        containsAny(source, "eye cream", "eye patch", "lip balm", "lip care") -> "Eye & Lip Care"
        containsAny(source, "ampoule", "essence", "serum") -> "Serum"
        containsAny(source, "toner", "mist") -> "Toner"
        containsAny(source, "cleansing oil", "cleansing foam", "cleanser", "face wash", "cleansing") -> "Cleanser"
        containsAny(source, "cream", "moisturizer", "moisturiser", "gel cream", "face cream", "lotion") -> "Cream"
        else -> "Skincare"
    }
}

private fun containsAny(
    source: String,
    vararg keywords: String,
): Boolean {
    return keywords.any { keyword ->
        source.contains(normalizeForCategoryDetection(keyword))
    }
}

private fun normalizeForCategoryDetection(value: String): String {
    return value
        .lowercase(Locale.ROOT)
        .replace("&", " and ")
        .replace("/", " ")
        .replace("-", " ")
        .replace("_", " ")
        .replace(Regex("[^a-z0-9 ]"), " ")
        .replace(Regex("\\s+"), " ")
        .trim()
}

private fun Int.formatCurrency(): String {
    return "₩" + "%,d".format(this)
}