package com.example.sabinacosmeticapplication.feature.categoryproducts

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CategoryProductsRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    onProductClick: (String) -> Unit,
    viewModel: CategoryProductsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CategoryProductsScreen(
        padding = padding,
        uiState = uiState,
        onBackClick = onBackClick,
        onProductClick = onProductClick,
        onRetryClick = viewModel::reload,
    )
}