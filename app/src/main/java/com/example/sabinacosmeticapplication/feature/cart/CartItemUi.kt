package com.example.sabinacosmeticapplication.feature.cart

data class CartItemUi(
    val productId: String,
    val title: String,
    val brand: String,
    val price: String,
    val priceValue: Int,
    val quantity: Int,
    val imageUrl: String,
    val oldPrice: String? = null,
    val discountLabel: String? = null
)