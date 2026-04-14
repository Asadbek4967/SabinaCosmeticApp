package com.example.sabinacosmeticapplication.feature.favorites

import com.example.sabinacosmeticapplication.data.model.Product

data class FavoritesUiState(
    val isLoading: Boolean = true,
    val products: List<Product> = emptyList(),
    val errorMessage: String? = null,
    val userMessage: String? = null
)