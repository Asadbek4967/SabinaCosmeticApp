package com.example.sabinacosmeticapplication.feature.home

import com.example.sabinacosmeticapplication.data.model.Product

data class HomeUiState(
    val flashSaleProducts: List<Product> = emptyList(),
    val bestSellerProducts: List<Product> = emptyList(),
    val recommendedProducts: List<Product> = emptyList()
)