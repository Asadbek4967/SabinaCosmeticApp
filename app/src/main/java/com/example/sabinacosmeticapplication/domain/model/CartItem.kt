package com.example.sabinacosmeticapplication.domain.model

data class CartItem(
    val productId: String,
    val title: String,
    val brand: String,
    val category: String,
    val imageUrl: String,
    val price: Int,
    val quantity: Int
)