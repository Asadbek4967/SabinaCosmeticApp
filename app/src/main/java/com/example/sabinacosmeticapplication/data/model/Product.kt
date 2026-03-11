package com.example.sabinacosmeticapplication.data.model

data class Product(
    val id: String,
    val title: String,
    val brand: String,
    val category: String,
    val price: String,
    val priceValue: Int,
    val originalPrice: String = "",
    val originalPriceValue: Int = 0,
    val discountPercent: Int = 0,
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val imageUrl: String = "",
    val description: String = "",
    val isBestSeller: Boolean = false,
    val isFlashSale: Boolean = false
)