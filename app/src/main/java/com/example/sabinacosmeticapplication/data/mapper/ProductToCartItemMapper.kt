package com.example.sabinacosmeticapplication.data.mapper

import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.domain.model.CartItem

fun Product.toCartItem(quantity: Int = 1): CartItem {
    return CartItem(
        productId = id,
        title = title,
        brand = brand,
        price = price,
        priceValue = priceValue,
        imageUrl = imageUrl,
        quantity = quantity
    )
}