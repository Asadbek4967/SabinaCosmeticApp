package com.example.sabinacosmeticapplication.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.domain.usecase.product.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<SearchUiEvent>()
    val events: SharedFlow<SearchUiEvent> = _events.asSharedFlow()

    init {
        loadInitialData()
    }

    fun onAction(action: SearchUiAction) {
        when (action) {
            is SearchUiAction.QueryChanged -> handleQueryChanged(action.query)
            is SearchUiAction.SearchSubmitted -> performSearch(action.query)
            is SearchUiAction.PopularKeywordClicked -> handlePopularKeywordClick(action.keyword)
            is SearchUiAction.RecentSearchRemoved -> removeRecentSearch(action.keyword)
            SearchUiAction.ClearQueryClicked -> clearQuery()
        }
    }

    private fun loadInitialData() {
        _uiState.value = _uiState.value.copy(
            recentSearches = listOf("Serum", "Toner", "Cleanser")
        )
    }

    private fun handleQueryChanged(newQuery: String) {
        _uiState.value = _uiState.value.copy(query = newQuery)

        if (newQuery.isBlank()) {
            clearQuery()
            return
        }

        performSearch(newQuery)
    }

    private fun performSearch(query: String = _uiState.value.query) {
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

            runCatching {
                searchProductsUseCase(trimmedQuery)
            }.onSuccess { results ->
                _uiState.value = _uiState.value.copy(
                    results = results,
                    isLoading = false,
                    recentSearches = updateRecentSearches(trimmedQuery),
                    errorMessage = null
                )
            }.onFailure { throwable ->
                val message = throwable.message ?: "Search failed"

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = message
                )

                _events.emit(SearchUiEvent.ShowError(message))
            }
        }
    }

    private fun handlePopularKeywordClick(keyword: String) {
        val trimmedKeyword = keyword.trim()
        _uiState.value = _uiState.value.copy(query = trimmedKeyword)
        performSearch(trimmedKeyword)
    }

    private fun clearQuery() {
        _uiState.value = _uiState.value.copy(
            query = "",
            results = emptyList(),
            isLoading = false,
            errorMessage = null
        )
    }

    private fun removeRecentSearch(keyword: String) {
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