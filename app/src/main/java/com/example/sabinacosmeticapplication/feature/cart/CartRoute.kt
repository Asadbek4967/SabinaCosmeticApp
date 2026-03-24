package com.example.sabinacosmeticapplication.feature.cart

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CartRoute(
    padding: PaddingValues,
    onCheckoutClick: () -> Unit
) {
    val viewModel: CartViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CartScreen(
        padding = padding,
        uiState = uiState,
        onIncreaseQuantity = viewModel::increaseQuantity,
        onDecreaseQuantity = viewModel::decreaseQuantity,
        onRemoveItem = viewModel::removeItem,
        onRestoreLastRemovedItem = viewModel::restoreLastRemovedItem,
        onClearLastRemovedItem = viewModel::clearLastRemovedItem,
        onClearCart = viewModel::clearCart,
        onCheckoutClick = onCheckoutClick
    )
}