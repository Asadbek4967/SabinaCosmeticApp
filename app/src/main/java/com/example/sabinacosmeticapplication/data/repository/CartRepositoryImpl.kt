package com.example.sabinacosmeticapplication.data.repository

import com.example.sabinacosmeticapplication.data.local.cart.CartDao
import com.example.sabinacosmeticapplication.data.mapper.toCartEntity
import com.example.sabinacosmeticapplication.data.mapper.toCartItem
import com.example.sabinacosmeticapplication.domain.model.CartItem
import com.example.sabinacosmeticapplication.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override val cartItems: Flow<List<CartItem>> =
        cartDao.observeCartItems().map { entities ->
            entities.map { it.toCartItem() }
        }

    override suspend fun addToCart(item: CartItem) {
        val existingItem = cartDao.getCartItemById(item.productId)

        if (existingItem == null) {
            cartDao.upsertCartItem(item.toCartEntity())
        } else {
            cartDao.upsertCartItem(
                existingItem.copy(quantity = existingItem.quantity + item.quantity)
            )
        }
    }

    override suspend fun removeFromCart(productId: String) {
        cartDao.deleteCartItemById(productId)
    }

    override suspend fun increaseQuantity(productId: String) {
        val item = cartDao.getCartItemById(productId) ?: return
        cartDao.upsertCartItem(item.copy(quantity = item.quantity + 1))
    }

    override suspend fun decreaseQuantity(productId: String) {
        val item = cartDao.getCartItemById(productId) ?: return

        if (item.quantity > 1) {
            cartDao.upsertCartItem(item.copy(quantity = item.quantity - 1))
        } else {
            cartDao.deleteCartItemById(productId)
        }
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }
}