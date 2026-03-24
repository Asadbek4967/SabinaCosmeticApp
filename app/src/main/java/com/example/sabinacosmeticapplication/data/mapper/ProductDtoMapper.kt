package com.example.sabinacosmeticapplication.data.mapper

import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.data.remote.dto.ProductDto

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        brand = brand,
        category = category,
        price = price,
        priceValue = priceValue,
        oldPrice = oldPrice,
        discountLabel = discountLabel,
        imageUrl = imageUrl,
        description = description,
        isFlashSale = isFlashSale,
        isBestSeller = isBestSeller
    )
}