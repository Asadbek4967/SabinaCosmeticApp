package com.example.sabinacosmeticapplication.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        _uiState.value = _uiState.value.copy(
            recentSearches = listOf("Serum", "Toner", "Cleanser")
        )
    }

    fun onQueryChange(newQuery: String) {
        _uiState.value = _uiState.value.copy(query = newQuery)
        performSearch(newQuery)
    }

    fun performSearch(query: String = _uiState.value.query) {
        val trimmedQuery = query.trim()

        if (trimmedQuery.isBlank()) {
            _uiState.value = _uiState.value.copy(
                query = "",
                results = emptyList(),
                isLoading = false,
                errorMessage = null
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                query = trimmedQuery,
                isLoading = true,
                errorMessage = null
            )

            try {
                val results = repository.searchProducts(trimmedQuery)

                _uiState.value = _uiState.value.copy(
                    results = results,
                    isLoading = false,
                    recentSearches = updateRecentSearches(trimmedQuery)
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Search failed"
                )
            }
        }
    }

    fun onPopularKeywordClick(keyword: String) {
        val trimmedKeyword = keyword.trim()

        _uiState.value = _uiState.value.copy(query = trimmedKeyword)
        performSearch(trimmedKeyword)
    }

    fun clearQuery() {
        _uiState.value = _uiState.value.copy(
            query = "",
            results = emptyList(),
            isLoading = false,
            errorMessage = null
        )
    }

    fun removeRecentSearch(keyword: String) {
        _uiState.value = _uiState.value.copy(
            recentSearches = _uiState.value.recentSearches.filterNot {
                it.equals(keyword, ignoreCase = true)
            }
        )
    }

    private fun updateRecentSearches(keyword: String): List<String> {
        val updated = listOf(keyword) + _uiState.value.recentSearches.filterNot {
            it.equals(keyword, ignoreCase = true)
        }
        return updated.take(8)
    }
}