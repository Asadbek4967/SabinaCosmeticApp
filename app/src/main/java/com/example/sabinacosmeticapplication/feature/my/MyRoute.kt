package com.example.sabinacosmeticapplication.feature.my

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MyRoute(
    padding: PaddingValues,
    onWishlistClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onLogoutCompleted: () -> Unit,
    viewModel: MyViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        for (event in viewModel.events) {
            when (event) {
                MyUiEvent.NavigateToWishlist -> onWishlistClick()
                MyUiEvent.NavigateToOrders -> onOrdersClick()
                MyUiEvent.LogoutCompleted -> onLogoutCompleted()
            }
        }
    }

    MyScreen(
        padding = padding,
        uiState = uiState,
        onAction = viewModel::onAction
    )
}