package com.example.sabinacosmeticapplication.feature.cart

import com.example.sabinacosmeticapplication.data.model.Product

data class CartItemUi(
    val product: Product,
    val quantity: Int
) {
    val totalPrice: Int
        get() = product.priceValue * quantity
}