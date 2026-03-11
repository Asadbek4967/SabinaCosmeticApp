package com.example.sabinacosmeticapplication.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CartViewModel : ViewModel() {

    val cartItems: StateFlow<List<CartItemUi>> =
        CartManager.repository.cartItems.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val cartItemCount: StateFlow<Int> =
        CartManager.repository.cartItems
            .map { items -> items.sumOf { it.quantity } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0
            )

    fun increaseQuantity(productId: String) {
        CartManager.repository.increaseQuantity(productId)
    }

    fun decreaseQuantity(productId: String) {
        CartManager.repository.decreaseQuantity(productId)
    }

    fun removeFromCart(productId: String) {
        CartManager.repository.removeFromCart(productId)
    }
}