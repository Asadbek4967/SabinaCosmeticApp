package com.example.sabinacosmeticapplication.feature.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sabinacosmeticapplication.data.repository.FakeProductRepository

@Composable
fun SearchRoute(
    padding: PaddingValues,
    onProductClick: (String) -> Unit
) {
    val viewModel: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(
            repository = FakeProductRepository()
        )
    )

    val uiState by viewModel.uiState.collectAsState()

    SearchScreen(
        uiState = uiState,
        onQueryChange = viewModel::onQueryChange,
        onSearch = viewModel::performSearch,
        onClearQuery = viewModel::clearQuery,
        onPopularKeywordClick = viewModel::onPopularKeywordClick,
        onRemoveRecentSearch = viewModel::removeRecentSearch,
        onProductClick = onProductClick,
        padding = padding
    )
}