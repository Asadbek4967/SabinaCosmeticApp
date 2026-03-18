package com.example.sabinacosmeticapplication.data.model

import androidx.annotation.DrawableRes

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
    val hasRemoteImage: Boolean
        get() = imageUrl.isNotBlank()

    val hasLocalImage: Boolean
        get() = imageRes != null

    val hasAnyImage: Boolean
        get() = hasRemoteImage || hasLocalImage
}