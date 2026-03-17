package com.example.sabinacosmeticapplication.feature.productdetail

sealed interface ProductDetailUiAction {
    data object AddToCartClicked : ProductDetailUiAction
}