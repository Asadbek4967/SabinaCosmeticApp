package com.example.sabinacosmeticapplication.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sabinacosmeticapplication.data.repository.FakeProductRepository
import com.example.sabinacosmeticapplication.data.repository.ProductRepository


class SearchViewModel(
    private val repository: ProductRepository = FakeProductRepository()
) : ViewModel() {

    var uiState by mutableStateOf(
        SearchUiState(
            results = repository.getAllProducts()
        )
    )
        private set

    fun onQueryChange(newQuery: String) {
        uiState = uiState.copy(
            query = newQuery,
            results = repository.searchProducts(newQuery)
        )
    }
}