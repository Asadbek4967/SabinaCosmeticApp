package com.example.sabinacosmeticapplication.feature.cart

data class CartUiState(
    val items: List<CartItemUi> = emptyList(),
    val totalPrice: Int = 0,
    val lastRemovedItem: CartItemUi? = null
) {
    val itemCount: Int
        get() = items.sumOf { it.quantity }

    val isEmpty: Boolean
        get() = items.isEmpty()
}