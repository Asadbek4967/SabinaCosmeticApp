package com.example.sabinacosmeticapplication.feature.productdetail

import com.example.sabinacosmeticapplication.data.model.Product

data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val isAddedToCart: Boolean = false,
    val errorMessage: String? = null
)