package com.example.sabinacosmeticapplication.feature.categoryproducts

import com.example.sabinacosmeticapplication.data.model.Product

data class CategoryProductsUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val fallbackProducts: List<Product> = emptyList(),
    val categoryKey: String = "",
    val categoryDisplayName: String = "",
    val categorySubtitle: String = "",
    val errorMessage: String? = null,
    val isFallbackMode: Boolean = false,
)