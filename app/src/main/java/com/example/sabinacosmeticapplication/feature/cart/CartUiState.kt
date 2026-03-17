package com.example.sabinacosmeticapplication.feature.cart

data class CartUiState(
    val items: List<CartItemUi> = emptyList(),
    val itemCount: Int = 0,
    val totalPrice: Int = 0,
    val lastRemovedItem: CartItemUi? = null
)