package com.example.sabinacosmeticapplication.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeRoute(
    padding: PaddingValues,
    onSearchClick: () -> Unit,
    onProductClick: (String) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        padding = padding,
        uiState = uiState,
        onSearchClick = onSearchClick,
        onProductClick = onProductClick
    )
}