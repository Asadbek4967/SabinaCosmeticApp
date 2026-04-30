package com.example.sabinacosmeticapplication.feature.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SearchRoute(
    padding: PaddingValues,
    onProductClick: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                is SearchUiEvent.ShowError -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    SearchScreen(
        uiState = uiState,
        onQueryChange = { query ->
            viewModel.onAction(SearchUiAction.QueryChanged(query))
        },
        onSearch = { query ->
            viewModel.onAction(SearchUiAction.SearchSubmitted(query))
        },
        onClearQuery = {
            viewModel.onAction(SearchUiAction.ClearQueryClicked)
        },
        onPopularKeywordClick = { keyword ->
            viewModel.onAction(SearchUiAction.PopularKeywordClicked(keyword))
        },
        onRecentSearchClick = { keyword ->
            viewModel.onAction(SearchUiAction.RecentSearchClicked(keyword))
        },
        onRemoveRecentSearch = { keyword ->
            viewModel.onAction(SearchUiAction.RecentSearchRemoved(keyword))
        },
        onProductClick = onProductClick,
        padding = padding,
        snackbarHostState = snackbarHostState
    )
}