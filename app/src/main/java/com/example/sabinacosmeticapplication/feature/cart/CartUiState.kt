package com.example.sabinacosmeticapplication.feature.cart

data class CartUiState(
    val items: List<CartItemUi> = emptyList(),
    val totalPrice: Int = 0,
    val totalItemCount: Int = 0,
    val isEmpty: Boolean = true,
    val lastRemovedItem: CartItemUi? = null
)