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
    val errorMessage: String? = null,
    val validationMessage: String? = null
) {
    companion object {
        const val MIN_QUERY_LENGTH = 2
        const val MAX_RECENT_SEARCHES = 8
    }

    val normalizedQuery: String
        get() = query.trim()

    val showValidationState: Boolean
        get() = validationMessage != null && !isLoading

    val showErrorState: Boolean
        get() = errorMessage != null && !isLoading && results.isEmpty()

    val showResults: Boolean
        get() = results.isNotEmpty()

    val showNoResultsState: Boolean
        get() = normalizedQuery.length >= MIN_QUERY_LENGTH &&
                results.isEmpty() &&
                !isLoading &&
                errorMessage == null &&
                validationMessage == null
}