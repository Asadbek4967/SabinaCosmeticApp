package com.example.sabinacosmeticapplication.feature.categoryproducts

import com.example.sabinacosmeticapplication.data.model.Product

data class CategoryProductsUiState(
    val categoryName: String = "",
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)