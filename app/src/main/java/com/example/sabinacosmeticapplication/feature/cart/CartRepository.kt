package com.example.sabinacosmeticapplication.feature.cart

import com.example.sabinacosmeticapplication.data.local.cart.CartDao
import com.example.sabinacosmeticapplication.data.local.cart.CartEntity
import com.example.sabinacosmeticapplication.data.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepository(
    private val cartDao: CartDao,
    private val productRepository: ProductRepository
) {

    val cartItems: Flow<List<CartItemUi>> =
        cartDao.observeCartItems().map { cartEntities ->
            val products = productRepository.getAllProducts()

            cartEntities.mapNotNull { entity ->
                val matchedProduct = products.find { product ->
                    product.id == entity.productId
                }

                matchedProduct?.let { product ->
                    CartItemUi(
                        product = product,
                        quantity = entity.quantity
                    )
                }
            }
        }

    suspend fun addToCart(productId: String) {
        val existingItem = cartDao.getCartItem(productId)

        if (existingItem == null) {
            cartDao.upsertCartItem(
                CartEntity(
                    productId = productId,
                    quantity = 1
                )
            )
        } else {
            cartDao.upsertCartItem(
                existingItem.copy(quantity = existingItem.quantity + 1)
            )
        }
    }

    suspend fun increaseQuantity(productId: String) {
        val existingItem = cartDao.getCartItem(productId) ?: return

        cartDao.upsertCartItem(
            existingItem.copy(quantity = existingItem.quantity + 1)
        )
    }

    suspend fun decreaseQuantity(productId: String) {
        val existingItem = cartDao.getCartItem(productId) ?: return

        if (existingItem.quantity <= 1) {
            cartDao.deleteCartItem(productId)
        } else {
            cartDao.upsertCartItem(
                existingItem.copy(quantity = existingItem.quantity - 1)
            )
        }
    }

    suspend fun removeFromCart(productId: String) {
        cartDao.deleteCartItem(productId)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}