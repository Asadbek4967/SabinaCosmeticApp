package com.example.sabinacosmeticapplication.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.domain.usecase.product.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<SearchUiEvent>()
    val events: SharedFlow<SearchUiEvent> = _events.asSharedFlow()

    private var searchJob: Job? = null

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 350L
        private val DEFAULT_RECENT_SEARCHES = listOf("Serum", "Toner", "Cleanser")
    }

    init {
        loadInitialData()
    }

    fun onAction(action: SearchUiAction) {
        when (action) {
            is SearchUiAction.QueryChanged -> handleQueryChanged(action.query)
            is SearchUiAction.SearchSubmitted -> handleSearchSubmitted(action.query)
            is SearchUiAction.PopularKeywordClicked -> triggerKeywordSearch(action.keyword)
            is SearchUiAction.RecentSearchClicked -> triggerKeywordSearch(action.keyword)
            is SearchUiAction.RecentSearchRemoved -> removeRecentSearch(action.keyword)
            SearchUiAction.ClearQueryClicked -> clearQuery()
        }
    }

    private fun loadInitialData() {
        _uiState.update { current ->
            current.copy(
                recentSearches = if (current.recentSearches.isEmpty()) {
                    DEFAULT_RECENT_SEARCHES
                } else {
                    current.recentSearches
                }
            )
        }
    }

    private fun handleQueryChanged(newQuery: String) {
        val normalizedQuery = normalizeQuery(newQuery)

        _uiState.update {
            it.copy(
                query = newQuery,
                errorMessage = null,
                validationMessage = null
            )
        }

        searchJob?.cancel()

        when {
            normalizedQuery.isBlank() -> {
                _uiState.update {
                    it.copy(
                        results = emptyList(),
                        isLoading = false,
                        errorMessage = null,
                        validationMessage = null
                    )
                }
            }

            normalizedQuery.length < SearchUiState.MIN_QUERY_LENGTH -> {
                _uiState.update {
                    it.copy(
                        results = emptyList(),
                        isLoading = false,
                        errorMessage = null,
                        validationMessage = "Enter at least ${SearchUiState.MIN_QUERY_LENGTH} characters"
                    )
                }
            }

            else -> {
                searchJob = viewModelScope.launch {
                    delay(SEARCH_DEBOUNCE_MS)
                    performSearchInternal(
                        query = normalizedQuery,
                        saveToRecentSearches = false
                    )
                }
            }
        }
    }

    private fun handleSearchSubmitted(query: String) {
        val normalizedQuery = normalizeQuery(query)

        searchJob?.cancel()

        when {
            normalizedQuery.isBlank() -> clearQuery()

            normalizedQuery.length < SearchUiState.MIN_QUERY_LENGTH -> {
                _uiState.update {
                    it.copy(
                        results = emptyList(),
                        isLoading = false,
                        errorMessage = null,
                        validationMessage = "Enter at least ${SearchUiState.MIN_QUERY_LENGTH} characters"
                    )
                }
            }

            else -> {
                viewModelScope.launch {
                    performSearchInternal(
                        query = normalizedQuery,
                        saveToRecentSearches = true
                    )
                }
            }
        }
    }

    private fun triggerKeywordSearch(keyword: String) {
        val normalizedKeyword = normalizeQuery(keyword)

        _uiState.update {
            it.copy(
                query = normalizedKeyword,
                errorMessage = null,
                validationMessage = null
            )
        }

        searchJob?.cancel()
        viewModelScope.launch {
            performSearchInternal(
                query = normalizedKeyword,
                saveToRecentSearches = true
            )
        }
    }

    private suspend fun performSearchInternal(
        query: String,
        saveToRecentSearches: Boolean
    ) {
        val normalizedQuery = normalizeQuery(query)

        if (normalizedQuery.isBlank()) {
            clearQuery()
            return
        }

        _uiState.update {
            it.copy(
                query = normalizedQuery,
                isLoading = true,
                results = emptyList(),
                errorMessage = null,
                validationMessage = null
            )
        }

        runCatching {
            searchProductsUseCase(normalizedQuery)
        }.onSuccess { results ->
            _uiState.update { current ->
                current.copy(
                    query = normalizedQuery,
                    results = results,
                    isLoading = false,
                    errorMessage = null,
                    validationMessage = null,
                    recentSearches = if (saveToRecentSearches && normalizedQuery.isNotBlank()) {
                        updateRecentSearches(
                            keyword = normalizedQuery,
                            currentRecentSearches = current.recentSearches
                        )
                    } else {
                        current.recentSearches
                    }
                )
            }
        }.onFailure { throwable ->
            val message = throwable.message ?: "Something went wrong while searching."

            _uiState.update {
                it.copy(
                    isLoading = false,
                    results = emptyList(),
                    errorMessage = message
                )
            }

            _events.emit(SearchUiEvent.ShowError(message))
        }
    }

    private fun clearQuery() {
        searchJob?.cancel()
        _uiState.update {
            it.copy(
                query = "",
                results = emptyList(),
                isLoading = false,
                errorMessage = null,
                validationMessage = null
            )
        }
    }

    private fun removeRecentSearch(keyword: String) {
        val normalizedKeyword = normalizeQuery(keyword)

        _uiState.update { current ->
            current.copy(
                recentSearches = current.recentSearches.filterNot {
                    normalizeQuery(it).equals(normalizedKeyword, ignoreCase = true)
                }
            )
        }
    }

    private fun updateRecentSearches(
        keyword: String,
        currentRecentSearches: List<String>
    ): List<String> {
        val normalizedKeyword = normalizeQuery(keyword)

        if (normalizedKeyword.isBlank()) return currentRecentSearches
        if (normalizedKeyword.length < SearchUiState.MIN_QUERY_LENGTH) return currentRecentSearches

        val updated = buildList {
            add(normalizedKeyword)

            currentRecentSearches.forEach { existing ->
                if (!normalizeQuery(existing).equals(normalizedKeyword, ignoreCase = true)) {
                    add(existing.trim())
                }
            }
        }

        return updated.take(SearchUiState.MAX_RECENT_SEARCHES)
    }

    private fun normalizeQuery(value: String): String {
        return value
            .trim()
            .replace("\\s+".toRegex(), " ")
    }
}