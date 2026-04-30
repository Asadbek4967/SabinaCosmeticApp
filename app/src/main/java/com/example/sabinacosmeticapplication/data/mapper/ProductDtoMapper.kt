package com.example.sabinacosmeticapplication.data.mapper

import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.data.remote.dto.ProductDto
import java.util.Locale
import kotlin.math.roundToInt

fun ProductDto.toProduct(): Product {
    val mainImage = resolveMainImage(
        thumbnailUrl = thumbnailUrl,
        imageUrl = imageUrl
    )

    val currentPriceValue = price.toWonValue()
    val oldPriceValue = oldPrice?.roundToInt()?.takeIf { it > 0 }

    val discount = resolveDiscountLabel(
        currentPriceValue = currentPriceValue,
        oldPriceValue = oldPriceValue
    )

    return Product(
        id = id.clean("unknown-product"),
        title = title.clean("Unknown Product"),
        brand = brand.clean("Unknown Brand"),
        category = category?.nameEn.clean("Cosmetic"),
        price = currentPriceValue.toWonText(),
        priceValue = currentPriceValue,
        oldPrice = oldPriceValue?.toWonText(),
        discountLabel = discount,
        imageUrl = mainImage,
        imageRes = null,
        description = description.clean(shortDescription.clean("Professional cosmetic product.")),
        isFlashSale = discount != null,
        isBestSeller = isBestSeller == true || isFeatured == true,
        benefits = null,
        howToUse = null,
        ingredients = null,
        warning = null,
        skinType = skinType?.trim()?.takeIf { it.isNotBlank() },
        galleryImages = listOfNotNull(mainImage.takeIf { it.isNotBlank() }),
        videos = emptyList()
    )
}

private fun resolveMainImage(
    thumbnailUrl: String?,
    imageUrl: String?
): String {
    return listOf(thumbnailUrl, imageUrl)
        .firstOrNull { !it.isNullOrBlank() }
        ?.trim()
        .orEmpty()
}

private fun resolveDiscountLabel(
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

private fun Double?.toWonValue(): Int {
    return this?.roundToInt()?.coerceAtLeast(0) ?: 0
}

private fun Int.toWonText(): String {
    return "₩" + "%,d".format(Locale.US, this)
}

private fun String?.clean(defaultValue: String): String {
    return this?.trim()?.takeIf { it.isNotBlank() } ?: defaultValue
}