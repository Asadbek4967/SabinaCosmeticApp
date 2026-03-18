package com.example.sabinacosmeticapplication.feature.cart

import androidx.annotation.DrawableRes

data class CartItemUi(
    val productId: String,
    val title: String,
    val brand: String,
    val category: String,
    val price: String,
    val priceValue: Int,
    val quantity: Int,
    val imageUrl: String = "",
    @DrawableRes val imageRes: Int? = null,
    val oldPrice: String? = null,
    val discountLabel: String? = null
)