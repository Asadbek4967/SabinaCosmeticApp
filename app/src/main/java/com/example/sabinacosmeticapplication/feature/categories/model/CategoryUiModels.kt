package com.example.sabinacosmeticapplication.feature.categories.model

import com.example.sabinacosmeticapplication.data.model.AppCategory

data class CategorySectionUi(
    val rootCategories: List<AppCategory> = emptyList(),
    val selectedRootCategoryId: String? = null,
    val selectedRootCategory: AppCategory? = null,
)