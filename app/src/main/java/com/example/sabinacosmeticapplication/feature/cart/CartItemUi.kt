package com.example.sabinacosmeticapplication.feature.cart

data class CartItemUi(
    val productId: String,
    val title: String,
    val brand: String,
    val price: String,
    val priceValue: Int,
    val imageUrl: String,
    val quantity: Int
) {
    val totalItemPrice: Int
        get() = priceValue * quantity
}