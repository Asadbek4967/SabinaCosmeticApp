package com.example.sabinacosmeticapplication.feature.cart

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object CartManager {

    private val _cartItems = MutableStateFlow<List<CartItemUi>>(emptyList())
    val cartItems: StateFlow<List<CartItemUi>> = _cartItems.asStateFlow()

    fun addToCart(product: CartProduct) {
        val currentItems = _cartItems.value
        val existingItem = currentItems.find { it.product.id == product.id }

        _cartItems.value = if (existingItem == null) {
            currentItems + CartItemUi(
                product = product,
                quantity = 1
            )
        } else {
            currentItems.map { item ->
                if (item.product.id == product.id) {
                    item.copy(quantity = item.quantity + 1)
                } else {
                    item
                }
            }
        }
    }

    fun removeFromCart(productId: String): CartItemUi? {
        val currentItems = _cartItems.value
        val removedItem = currentItems.find { it.product.id == productId } ?: return null

        _cartItems.value = currentItems.filterNot { it.product.id == productId }
        return removedItem
    }

    fun increaseQuantity(productId: String) {
        _cartItems.value = _cartItems.value.map { item ->
            if (item.product.id == productId) {
                item.copy(quantity = item.quantity + 1)
            } else {
                item
            }
        }
    }

    fun decreaseQuantity(productId: String) {
        _cartItems.value = _cartItems.value.mapNotNull { item ->
            if (item.product.id == productId) {
                val newQuantity = item.quantity - 1
                if (newQuantity <= 0) {
                    null
                } else {
                    item.copy(quantity = newQuantity)
                }
            } else {
                item
            }
        }
    }

    fun restoreItem(item: CartItemUi) {
        val existingItem = _cartItems.value.find { it.product.id == item.product.id }

        _cartItems.value = if (existingItem == null) {
            listOf(item) + _cartItems.value
        } else {
            _cartItems.value.map { current ->
                if (current.product.id == item.product.id) {
                    current.copy(quantity = current.quantity + item.quantity)
                } else {
                    current
                }
            }
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}