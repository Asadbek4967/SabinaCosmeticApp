package com.example.sabinacosmeticapplication.feature.categories

import com.example.sabinacosmeticapplication.data.model.AppCategory

data class CategoryUiState(
    val isLoading: Boolean = false,
    val rootCategories: List<AppCategory> = emptyList(),
    val selectedRootCategoryId: String? = null,
    val selectedRootCategory: AppCategory? = null,
    val expandedCategoryId: String? = null,
    val errorMessage: String? = null,
)