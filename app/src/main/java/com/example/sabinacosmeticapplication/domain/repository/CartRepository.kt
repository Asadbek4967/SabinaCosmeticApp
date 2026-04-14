package com.example.sabinacosmeticapplication.domain.repository

import com.example.sabinacosmeticapplication.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun observeCartItems(): Flow<List<CartItem>>
    suspend fun getCartItemsOnce(): List<CartItem>
    suspend fun addToCart(item: CartItem)
    suspend fun updateQuantity(productId: String, quantity: Int)
    suspend fun removeFromCart(productId: String)
    suspend fun clearCart()
    fun observeCartBadgeCount(): Flow<Int>
}