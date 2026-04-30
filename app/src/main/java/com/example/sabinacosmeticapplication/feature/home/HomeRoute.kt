package com.example.sabinacosmeticapplication.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    padding: PaddingValues,
    onSearchClick: () -> Unit,
    onProductClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onCategoriesSeeAllClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        padding = padding,
        uiState = uiState,
        onAction = { action ->
            when (action) {
                is HomeUiAction.ProductClick -> onProductClick(action.productId)
                is HomeUiAction.CategoryClick -> onCategoryClick(action.category)
                HomeUiAction.SearchClick -> onSearchClick()
                HomeUiAction.CategoriesSeeAllClick -> onCategoriesSeeAllClick()
                HomeUiAction.RetryClick -> viewModel.onAction(HomeUiAction.RetryClick)
            }
        }
    )
}