package com.example.sabinacosmeticapplication.feature.productdetail

sealed interface ProductDetailUiAction {
    data object AddToCartClick : ProductDetailUiAction
    data object ToggleFavoriteClick : ProductDetailUiAction
    data object ToggleDescriptionClick : ProductDetailUiAction
    data object IncreaseQuantityClick : ProductDetailUiAction
    data object DecreaseQuantityClick : ProductDetailUiAction
    data object RetryClick : ProductDetailUiAction
}