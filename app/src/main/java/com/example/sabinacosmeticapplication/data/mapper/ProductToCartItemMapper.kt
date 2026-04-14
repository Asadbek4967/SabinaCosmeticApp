package com.example.sabinacosmeticapplication.data.mapper

import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.domain.model.CartItem

fun Product.toCartItem(quantity: Int = 1): CartItem {
    val safePrice = priceValue.coerceAtLeast(0)
    val safeQuantity = quantity.coerceAtLeast(1)

    return CartItem(
        productId = id.trim(),
        title = safeTitle,
        brand = safeBrand,
        category = category.trim(),
        imageUrl = imageUrl.trim(),
        price = safePrice,
        quantity = safeQuantity
    )
}