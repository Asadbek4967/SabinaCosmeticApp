package com.example.sabinacosmeticapplication.feature.search

import com.example.sabinacosmeticapplication.data.model.Product

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val results: List<Product> = emptyList(),
    val recentSearches: List<String> = emptyList(),
    val popularKeywords: List<String> = listOf(
        "Serum",
        "Toner",
        "Cream",
        "Lip Care",
        "Sun Care"
    ),
    val selectedCategory: String? = null,
    val errorMessage: String? = null
) {
    val isQueryEmpty: Boolean
        get() = query.isBlank()

    val showEmptyQueryState: Boolean
        get() = query.isBlank() && recentSearches.isNotEmpty()

    val showNoResultsState: Boolean
        get() = query.isNotBlank() && results.isEmpty() && !isLoading

    val showResults: Boolean
        get() = results.isNotEmpty()
}