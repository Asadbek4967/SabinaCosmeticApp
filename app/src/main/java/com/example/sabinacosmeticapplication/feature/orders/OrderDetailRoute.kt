package com.example.sabinacosmeticapplication.feature.orders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun OrderDetailRoute(
    onBackClick: () -> Unit,
    viewModel: OrderDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    OrderDetailScreen(
        uiState = uiState,
        onBackClick = onBackClick
    )
}