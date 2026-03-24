package com.example.sabinacosmeticapplication.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.domain.model.CartItem
import com.example.sabinacosmeticapplication.domain.usecase.cart.AddToCartUseCase
import com.example.sabinacosmeticapplication.domain.usecase.cart.ClearCartUseCase
import com.example.sabinacosmeticapplication.domain.usecase.cart.DecreaseCartItemQuantityUseCase
import com.example.sabinacosmeticapplication.domain.usecase.cart.GetCartItemsUseCase
import com.example.sabinacosmeticapplication.domain.usecase.cart.IncreaseCartItemQuantityUseCase
import com.example.sabinacosmeticapplication.domain.usecase.cart.RemoveFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class CartViewModel @Inject constructor(
    getCartItemsUseCase: GetCartItemsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val increaseCartItemQuantityUseCase: IncreaseCartItemQuantityUseCase,
    private val decreaseCartItemQuantityUseCase: DecreaseCartItemQuantityUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {

    private val lastRemovedItem = MutableStateFlow<CartItemUi?>(null)

    val uiState: StateFlow<CartUiState> = combine(
        getCartItemsUseCase(),
        lastRemovedItem
    ) { cartItems, removedItem ->

        val itemsUi = cartItems.map { it.toCartItemUi() }
        val totalPrice = itemsUi.sumOf { it.totalItemPrice }
        val totalItemCount = itemsUi.sumOf { it.quantity }

        CartUiState(
            items = itemsUi,
            totalPrice = totalPrice,
            totalItemCount = totalItemCount,
            isEmpty = itemsUi.isEmpty(),
            lastRemovedItem = removedItem
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CartUiState()
    )

    fun increaseQuantity(productId: String) {
        viewModelScope.launch {
            increaseCartItemQuantityUseCase(productId)
        }
    }

    fun decreaseQuantity(productId: String) {
        viewModelScope.launch {
            decreaseCartItemQuantityUseCase(productId)
        }
    }

    fun removeItem(item: CartItemUi) {
        viewModelScope.launch {
            removeFromCartUseCase(item.productId)
            lastRemovedItem.value = item
        }
    }

    fun restoreLastRemovedItem() {
        val item = lastRemovedItem.value ?: return

        viewModelScope.launch {
            addToCartUseCase(item.toDomain())
            lastRemovedItem.value = null
        }
    }

    fun clearLastRemovedItem() {
        lastRemovedItem.value = null
    }

    fun clearCart() {
        viewModelScope.launch {
            clearCartUseCase()
            lastRemovedItem.value = null
        }
    }

    private fun CartItem.toCartItemUi(): CartItemUi {
        return CartItemUi(
            productId = productId,
            title = title,
            brand = brand,
            price = price,
            priceValue = priceValue,
            imageUrl = imageUrl,
            quantity = quantity
        )
    }

    private fun CartItemUi.toDomain(): CartItem {
        return CartItem(
            productId = productId,
            title = title,
            brand = brand,
            price = price,
            priceValue = priceValue,
            imageUrl = imageUrl,
            quantity = quantity
        )
    }
}