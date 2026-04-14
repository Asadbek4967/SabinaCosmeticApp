package com.example.sabinacosmeticapplication.feature.checkout

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CheckoutRoute(
    snackbarHostState: SnackbarHostState,
    onOrderPlaced: () -> Unit,
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                CheckoutEvent.OrderPlaced -> onOrderPlaced()
                is CheckoutEvent.ShowError -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    CheckoutScreen(
        uiState = uiState,
        onPlaceOrderClick = viewModel::placeOrder
    )
}