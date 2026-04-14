package com.example.sabinacosmeticapplication.data.repository

import com.example.sabinacosmeticapplication.data.local.cart.CartDao
import com.example.sabinacosmeticapplication.data.mapper.toCartEntity
import com.example.sabinacosmeticapplication.data.mapper.toCartItem
import com.example.sabinacosmeticapplication.domain.model.CartItem
import com.example.sabinacosmeticapplication.domain.repository.CartRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun observeCartItems(): Flow<List<CartItem>> {
        return cartDao.observeCartItems().map { entities ->
            entities.map { entity -> entity.toCartItem() }
        }
    }

    override suspend fun getCartItemsOnce(): List<CartItem> {
        return cartDao.getCartItemsOnce().map { entity ->
            entity.toCartItem()
        }
    }

    override suspend fun addToCart(item: CartItem) {
        val existingItem = cartDao.getCartItemByProductId(item.productId)

        if (existingItem == null) {
            cartDao.insert(item.toCartEntity())
        } else {
            cartDao.insert(
                existingItem.copy(
                    title = item.title,
                    brand = item.brand,
                    category = item.category,
                    imageUrl = item.imageUrl,
                    price = item.price,
                    quantity = existingItem.quantity + item.quantity
                )
            )
        }
    }

    override suspend fun updateQuantity(productId: String, quantity: Int) {
        if (quantity <= 0) {
            cartDao.deleteByProductId(productId)
        } else {
            cartDao.updateQuantity(productId, quantity)
        }
    }

    override suspend fun removeFromCart(productId: String) {
        cartDao.deleteByProductId(productId)
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }

    override fun observeCartBadgeCount(): Flow<Int> {
        return cartDao.observeCartBadgeCount()
    }
}