package com.example.sabinacosmeticapplication.feature.cart

import com.example.sabinacosmeticapplication.data.local.cart.CartDao
import com.example.sabinacosmeticapplication.data.local.cart.CartEntity
import com.example.sabinacosmeticapplication.data.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

@Singleton
class CartRepository @Inject constructor(
    private val cartDao: CartDao,
    private val productRepository: ProductRepository
) {

    private val _lastRemovedItem = MutableStateFlow<CartItemUi?>(null)
    val lastRemovedItem: StateFlow<CartItemUi?> = _lastRemovedItem

    val cartItems: Flow<List<CartItemUi>> =
        cartDao.observeCartItems().map { entities ->
            entities.mapNotNull { entity ->
                val product = productRepository.getProductById(entity.productId)
                    ?: return@mapNotNull null

                CartItemUi(
                    productId = product.id,
                    title = product.title,
                    brand = product.brand,
                    price = product.price,
                    priceValue = product.priceValue,
                    quantity = entity.quantity,
                    imageUrl = product.imageUrl,
                    oldPrice = product.oldPrice,
                    discountLabel = product.discountLabel
                )
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
                existingItem.copy(
                    quantity = existingItem.quantity + 1
                )
            )
        }
    }

    suspend fun increaseQuantity(productId: String) {
        val existingItem = cartDao.getCartItem(productId) ?: return

        cartDao.upsertCartItem(
            existingItem.copy(
                quantity = existingItem.quantity + 1
            )
        )
    }

    suspend fun decreaseQuantity(productId: String) {
        val existingItem = cartDao.getCartItem(productId) ?: return

        if (existingItem.quantity > 1) {
            cartDao.upsertCartItem(
                existingItem.copy(
                    quantity = existingItem.quantity - 1
                )
            )
        } else {
            removeFromCart(productId)
        }
    }

    suspend fun removeFromCart(productId: String) {
        val existingItem = cartDao.getCartItem(productId) ?: return
        val product = productRepository.getProductById(productId) ?: return

        _lastRemovedItem.value = CartItemUi(
            productId = product.id,
            title = product.title,
            brand = product.brand,
            price = product.price,
            priceValue = product.priceValue,
            quantity = existingItem.quantity,
            imageUrl = product.imageUrl,
            oldPrice = product.oldPrice,
            discountLabel = product.discountLabel
        )

        cartDao.deleteCartItem(productId)
    }

    suspend fun restoreLastRemovedItem() {
        val removedItem = _lastRemovedItem.value ?: return
        val existingItem = cartDao.getCartItem(removedItem.productId)

        if (existingItem == null) {
            cartDao.upsertCartItem(
                CartEntity(
                    productId = removedItem.productId,
                    quantity = removedItem.quantity
                )
            )
        } else {
            cartDao.upsertCartItem(
                existingItem.copy(
                    quantity = existingItem.quantity + removedItem.quantity
                )
            )
        }

        _lastRemovedItem.value = null
    }

    fun clearLastRemovedItemState() {
        _lastRemovedItem.value = null
    }

    suspend fun clearCart() {
        cartDao.clearCart()
        _lastRemovedItem.value = null
    }
}