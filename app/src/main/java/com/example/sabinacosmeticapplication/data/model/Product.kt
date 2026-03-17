package com.example.sabinacosmeticapplication.data.model

import retrofit2.http.Url

data class Product(
    val id: String,
    val title: String,
    val brand: String,
    val category: String,
    val price: String,
    val priceValue: Int,
    val oldPrice: String? = null,
    val discountLabel: String? = null,
    val imageUrl: String,
    val description: String,
    val isFlashSale: Boolean = false,
    val isBestSeller: Boolean = false

)