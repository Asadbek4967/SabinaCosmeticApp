package com.example.sabinacosmeticapplication.data.mapper

import com.example.sabinacosmeticapplication.data.local.cart.CartEntity
import com.example.sabinacosmeticapplication.domain.model.CartItem

fun CartEntity.toCartItem(): CartItem {
    return CartItem(
        productId = productId,
        title = title,
        brand = brand,
        price = price,
        priceValue = priceValue,
        imageUrl = imageUrl,
        quantity = quantity
    )
}

fun CartItem.toCartEntity(): CartEntity {
    return CartEntity(
        productId = productId,
        title = title,
        brand = brand,
        price = price,
        priceValue = priceValue,
        imageUrl = imageUrl,
        quantity = quantity
    )
}