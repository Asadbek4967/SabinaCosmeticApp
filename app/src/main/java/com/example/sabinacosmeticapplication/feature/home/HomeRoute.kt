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
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        padding = padding,
        uiState = uiState,
        onAction = { action ->
            viewModel.onAction(action)

            when (action) {
                HomeUiAction.SearchClick -> onSearchClick()
                is HomeUiAction.ProductClick -> onProductClick(action.productId)
            }
        }
    )
}