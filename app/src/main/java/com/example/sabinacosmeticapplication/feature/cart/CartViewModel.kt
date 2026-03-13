package com.example.sabinacosmeticapplication.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CartViewModel : ViewModel() {

    val cartItems: StateFlow<List<CartItemUi>> = CartManager.cartItems
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _lastRemovedItem = MutableStateFlow<CartItemUi?>(null)
    val lastRemovedItem: StateFlow<CartItemUi?> = _lastRemovedItem.asStateFlow()

    val cartItemCount: StateFlow<Int> = cartItems
        .map { items ->
            items.sumOf { item -> item.quantity }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    fun increaseQuantity(productId: String) {
        CartManager.increaseQuantity(productId)
    }

    fun decreaseQuantity(productId: String) {
        CartManager.decreaseQuantity(productId)
    }

    fun removeFromCart(productId: String) {
        _lastRemovedItem.value = CartManager.removeFromCart(productId)
    }

    fun undoRemove() {
        val removedItem = _lastRemovedItem.value ?: return
        CartManager.restoreItem(removedItem)
        _lastRemovedItem.value = null
    }

    fun clearLastRemovedItem() {
        _lastRemovedItem.value = null
    }

    fun clearCart() {
        CartManager.clearCart()
        _lastRemovedItem.value = null
    }
}