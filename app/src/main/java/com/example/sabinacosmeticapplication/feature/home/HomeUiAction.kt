package com.example.sabinacosmeticapplication.feature.home

sealed interface HomeUiAction {
    data class ProductClick(val productId: String) : HomeUiAction
    data class CategoryClick(val category: String) : HomeUiAction

    data object SearchClick : HomeUiAction
    data object CategoriesSeeAllClick : HomeUiAction
    data object RetryClick : HomeUiAction
}