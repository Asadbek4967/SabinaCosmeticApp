package com.example.sabinacosmeticapplication.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {

    val uiState: StateFlow<CartUiState> =
        combine(
            repository.cartItems,
            repository.lastRemovedItem
        ) { items, lastRemovedItem ->
            CartUiState(
                items = items,
                totalPrice = items.sumOf { it.priceValue * it.quantity },
                lastRemovedItem = lastRemovedItem
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CartUiState()
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
            repository.removeFromCart(productId)
        }
    }

    fun restoreLastRemovedItem() {
        viewModelScope.launch {
            repository.restoreLastRemovedItem()
        }
    }

    fun clearLastRemovedItem() {
        repository.clearLastRemovedItem()
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}