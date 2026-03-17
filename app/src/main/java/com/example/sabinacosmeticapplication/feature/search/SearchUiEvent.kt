package com.example.sabinacosmeticapplication.feature.search

sealed interface SearchUiEvent {
    data class ShowError(val message: String) : SearchUiEvent
}