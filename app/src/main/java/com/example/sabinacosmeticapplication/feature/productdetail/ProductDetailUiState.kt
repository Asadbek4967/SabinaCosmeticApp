package com.example.sabinacosmeticapplication.feature.productdetail

import com.example.sabinacosmeticapplication.data.model.Product

data class ProductDetailUiState(
    val isLoading: Boolean = true,
    val product: Product? = null,
    val errorMessage: String? = null,
    val isFavorite: Boolean = false,
    val quantity: Int = 1,
    val isDescriptionExpanded: Boolean = false
) {
    val showContent: Boolean
        get() = !isLoading && product != null

    val showErrorState: Boolean
        get() = !isLoading && product == null

    val safeQuantity: Int
        get() = quantity.coerceAtLeast(1)
}