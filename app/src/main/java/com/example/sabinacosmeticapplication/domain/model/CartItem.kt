package com.example.sabinacosmeticapplication.domain.model

data class CartItem(
    val productId: String,
    val title: String,
    val brand: String,
    val price: String,
    val priceValue: Int,
    val imageUrl: String,
    val quantity: Int
)