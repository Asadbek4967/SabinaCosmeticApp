package com.example.sabinacosmeticapplication.feature.orders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun OrdersRoute(
    onBackClick: () -> Unit,
    onOrderClick: (String) -> Unit,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    OrdersScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onOrderClick = onOrderClick
    )
}