package com.example.sabinacosmeticapplication.feature.productdetail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProductDetailRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    viewModel: ProductDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProductDetailScreen(
        padding = padding,
        uiState = uiState,
        onBackClick = onBackClick,
        onAddToCart = { viewModel.addToCart() }
    )
}