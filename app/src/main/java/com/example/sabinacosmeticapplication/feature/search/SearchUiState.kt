package com.example.sabinacosmeticapplication.feature.search

import com.example.sabinacosmeticapplication.data.model.Product

data class SearchUiState(
    val query: String = "",
    val results: List<Product> = emptyList()
)