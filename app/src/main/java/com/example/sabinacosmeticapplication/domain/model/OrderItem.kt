package com.example.sabinacosmeticapplication.domain.model

data class OrderItem(
    val productId: String,
    val title: String,
    val brand: String,
    val imageUrl: String,
    val quantity: Int,
    val priceValue: Int
)