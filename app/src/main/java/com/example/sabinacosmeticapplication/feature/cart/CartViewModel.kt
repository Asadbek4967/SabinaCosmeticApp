package com.example.sabinacosmeticapplication.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: CartRepository
) : ViewModel() {

    private val _lastRemovedItem = MutableStateFlow<CartItemUi?>(null)
    val lastRemovedItem: StateFlow<CartItemUi?> = _lastRemovedItem

    val cartItems: StateFlow<List<CartItemUi>> =
        repository.cartItems.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val cartItemCount: StateFlow<Int> =
        cartItems
            .map { items -> items.sumOf { it.quantity } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = 0
            )

    fun addToCart(productId: String) {
        viewModelScope.launch {
            repository.addToCart(productId)
        }
    }

    fun increaseQuantity(productId: String) {
        viewModelScope.launch {
            repository.increaseQuantity(productId)
        }
    }

    fun decreaseQuantity(productId: String) {
        viewModelScope.launch {
            repository.decreaseQuantity(productId)
        }
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            val removedItem = cartItems.value.find { it.product.id == productId }
            _lastRemovedItem.value = removedItem
            repository.removeFromCart(productId)
        }
    }

    fun undoRemove() {
        viewModelScope.launch {
            val removedItem = _lastRemovedItem.value ?: return@launch

            repeat(removedItem.quantity) {
                repository.addToCart(removedItem.product.id)
            }

            _lastRemovedItem.value = null
        }
    }

    fun clearLastRemovedItem() {
        _lastRemovedItem.value = null
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}