package com.example.sabinacosmeticapplication.feature.cart

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CartRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit = {},
    onCheckoutClick: () -> Unit = {},
    viewModel: CartViewModel
) {
    CartScreen(
        modifier = Modifier.padding(padding),
        onBackClick = onBackClick,
        onCheckoutClick = onCheckoutClick,
        viewModel = viewModel
    )
}