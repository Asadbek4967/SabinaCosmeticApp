package com.example.sabinacosmeticapplication.domain.repository

import com.example.sabinacosmeticapplication.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    val cartItems: Flow<List<CartItem>>

    suspend fun addToCart(item: CartItem)
    suspend fun removeFromCart(productId: String)
    suspend fun increaseQuantity(productId: String)
    suspend fun decreaseQuantity(productId: String)
    suspend fun clearCart()
}