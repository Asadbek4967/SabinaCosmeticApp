package com.example.sabinacosmeticapplication.feature.cart

import com.example.sabinacosmeticapplication.core.util.PriceFormatter

data class CartItemUi(
    val productId: String,
    val title: String,
    val brand: String,
    val category: String,
    val priceText: String,
    val priceValue: Int,
    val imageUrl: String,
    val quantity: Int
) {

    val totalItemPrice: Int
        get() = priceValue * quantity

    val totalItemPriceText: String
        get() = PriceFormatter.formatWon(totalItemPrice)
}