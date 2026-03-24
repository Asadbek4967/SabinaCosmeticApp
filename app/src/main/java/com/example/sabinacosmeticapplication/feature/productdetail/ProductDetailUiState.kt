package com.example.sabinacosmeticapplication.feature.productdetail

import com.example.sabinacosmeticapplication.data.model.Product

data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val errorMessage: String? = null,
    val isAddedToCart: Boolean = false
)