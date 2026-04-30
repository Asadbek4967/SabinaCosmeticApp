package com.example.sabinacosmeticapplication.feature.favorites

import androidx.compose.runtime.Composable

@Composable
fun FavoritesRoute(
    onBackClick: () -> Unit,
    onProductClick: (String) -> Unit
) {
    FavoritesScreen(
        onBackClick = onBackClick,
        onProductClick = onProductClick
    )
}