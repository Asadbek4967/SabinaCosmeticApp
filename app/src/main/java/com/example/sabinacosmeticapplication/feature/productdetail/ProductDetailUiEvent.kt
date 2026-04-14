package com.example.sabinacosmeticapplication.feature.productdetail

sealed interface ProductDetailUiEvent {
    data class ShowMessage(
        val message: String
    ) : ProductDetailUiEvent
}