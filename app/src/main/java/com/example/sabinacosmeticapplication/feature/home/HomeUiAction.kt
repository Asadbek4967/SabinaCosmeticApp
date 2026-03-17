package com.example.sabinacosmeticapplication.feature.home

sealed interface HomeUiAction {
    data object SearchClick : HomeUiAction
    data class ProductClick(val productId: String) : HomeUiAction
}