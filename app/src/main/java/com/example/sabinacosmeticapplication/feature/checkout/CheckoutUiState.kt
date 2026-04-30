package com.example.sabinacosmeticapplication.feature.checkout

import com.example.sabinacosmeticapplication.domain.model.CartItem

data class CheckoutUiState(
    val isLoading: Boolean = true,
    val items: List<CartItem> = emptyList(),
    val subtotalPrice: Int = 0,
    val shippingPrice: Int = 0,
    val totalPrice: Int = 0
)