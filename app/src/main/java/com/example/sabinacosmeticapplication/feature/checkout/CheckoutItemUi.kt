package com.example.sabinacosmeticapplication.feature.checkout

data class CheckoutItemUi(
    val productId: String,
    val title: String,
    val brand: String,
    val imageUrl: String,
    val quantity: Int,
    val priceValue: Int
) {
    val totalItemPrice: Int
        get() = quantity * priceValue
}