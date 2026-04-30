package com.example.sabinacosmeticapplication.feature.categories

sealed interface CategoryUiAction {
    data class SelectRootCategory(val categoryId: String) : CategoryUiAction
    data class ToggleExpandCategory(val categoryId: String) : CategoryUiAction
    data class OpenCategory(
        val categoryId: String,
        val categoryTitle: String,
    ) : CategoryUiAction

    data object RetryLoad : CategoryUiAction
}