package com.example.sabinacosmeticapplication.feature.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.domain.model.CartItem
import com.example.sabinacosmeticapplication.domain.repository.CartRepository
import com.example.sabinacosmeticapplication.domain.repository.OrderLineRequest
import com.example.sabinacosmeticapplication.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<CheckoutEvent>()
    val events: SharedFlow<CheckoutEvent> = _events.asSharedFlow()

    init {
        observeCart()
    }

    private fun observeCart() {
        viewModelScope.launch {
            cartRepository.observeCartItems().collect { items: List<CartItem> ->
                val subtotal = items.sumOf { item -> item.price * item.quantity }
                val shipping = when {
                    items.isEmpty() -> 0
                    subtotal >= 50000 -> 0
                    else -> 3000
                }
                val total = subtotal + shipping

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        items = items,
                        subtotalPrice = subtotal,
                        shippingPrice = shipping,
                        totalPrice = total
                    )
                }
            }
        }
    }

    fun placeOrder() {
        viewModelScope.launch {
            try {
                val cartItems = cartRepository.getCartItemsOnce()

                if (cartItems.isEmpty()) {
                    _events.emit(CheckoutEvent.ShowError("Cart is empty"))
                    return@launch
                }

                val subtotal = cartItems.sumOf { item -> item.price * item.quantity }
                val shipping = if (subtotal >= 50000) 0 else 3000
                val total = subtotal + shipping

                val orderLines = cartItems.map { item ->
                    OrderLineRequest(
                        productId = item.productId,
                        title = item.title,
                        brand = item.brand,
                        category = item.category,
                        imageUrl = item.imageUrl,
                        price = item.price,
                        quantity = item.quantity
                    )
                }

                orderRepository.placeOrder(
                    items = orderLines,
                    subtotalPrice = subtotal,
                    shippingPrice = shipping,
                    totalPrice = total
                )

                cartRepository.clearCart()
                _events.emit(CheckoutEvent.OrderPlaced)

            } catch (e: Exception) {
                _events.emit(
                    CheckoutEvent.ShowError(
                        e.message ?: "Failed to place order"
                    )
                )
            }
        }
    }
}