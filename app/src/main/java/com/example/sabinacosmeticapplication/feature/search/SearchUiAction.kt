package com.example.sabinacosmeticapplication.feature.search

sealed interface SearchUiAction {
    data class QueryChanged(val query: String) : SearchUiAction
    data class SearchSubmitted(val query: String) : SearchUiAction
    data class PopularKeywordClicked(val keyword: String) : SearchUiAction
    data class RecentSearchRemoved(val keyword: String) : SearchUiAction
    data object ClearQueryClicked : SearchUiAction
}