package com.example.sabinacosmeticapplication.feature.cart

data class CartProduct(
    val id: String,
    val title: String,
    val brand: String,
    val category: String,
    val price: String,
    val priceValue: Int,
    val imageUrl: String
)

data class CartItemUi(
    val product: CartProduct,
    val quantity: Int
)