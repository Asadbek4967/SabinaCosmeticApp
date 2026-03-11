package com.example.sabinacosmeticapplication.feature.cart

import com.example.sabinacosmeticapplication.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartRepository {

    private val _cartItems = MutableStateFlow<List<CartItemUi>>(emptyList())
    val cartItems: StateFlow<List<CartItemUi>> = _cartItems.asStateFlow()

    fun addToCart(product: Product) {
        val currentItems = _cartItems.value.toMutableList()
        val existingIndex = currentItems.indexOfFirst { it.product.id == product.id }

        if (existingIndex >= 0) {
            val existingItem = currentItems[existingIndex]
            currentItems[existingIndex] = existingItem.copy(
                quantity = existingItem.quantity + 1
            )
        } else {
            currentItems.add(
                CartItemUi(
                    product = product,
                    quantity = 1
                )
            )
        }

        _cartItems.value = currentItems
    }

    fun increaseQuantity(productId: String) {
        _cartItems.value = _cartItems.value.map { item ->
            if (item.product.id == productId) item.copy(quantity = item.quantity + 1) else item
        }
    }

    fun decreaseQuantity(productId: String) {
        _cartItems.value = _cartItems.value.mapNotNull { item ->
            if (item.product.id == productId) {
                val newQuantity = item.quantity - 1
                if (newQuantity <= 0) null else item.copy(quantity = newQuantity)
            } else {
                item
            }
        }
    }

    fun removeFromCart(productId: String) {
        _cartItems.value = _cartItems.value.filterNot { it.product.id == productId }
    }
}