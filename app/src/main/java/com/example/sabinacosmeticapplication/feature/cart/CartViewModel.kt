package com.example.sabinacosmeticapplication.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.core.util.PriceFormatter
import com.example.sabinacosmeticapplication.domain.model.CartItem
import com.example.sabinacosmeticapplication.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<CartUiEvent>(
        extraBufferCapacity = 1
    )
    val events: SharedFlow<CartUiEvent> = _events.asSharedFlow()

    private var observeCartJob: Job? = null
    private var lastRemovedItem: CartItemUi? = null

    init {
        observeCart()
    }

    fun onAction(action: CartUiAction) {
        when (action) {
            is CartUiAction.IncreaseQuantity -> increaseQuantity(action.productId)
            is CartUiAction.DecreaseQuantity -> decreaseQuantity(action.productId)
            is CartUiAction.RemoveItem -> removeItem(action.item)

            CartUiAction.ClearCartClicked -> showClearCartDialog()
            CartUiAction.ClearCartDismissed -> hideClearCartDialog()
            CartUiAction.ClearCartConfirmed -> clearCart()

            CartUiAction.UndoRemoveClicked -> undoRemove()
            CartUiAction.CheckoutClicked -> checkout()
            CartUiAction.StartShoppingClicked -> navigateToHome()
        }
    }

    fun retry() {
        clearError()
        setLoading(true)
        observeCart()
    }

    private fun observeCart() {
        observeCartJob?.cancel()

        observeCartJob = viewModelScope.launch {
            setLoading(true)

            cartRepository.observeCartItems()
                .catch { throwable ->
                    handleStateError(
                        message = throwable.message ?: "Unable to load cart"
                    )
                }
                .collectLatest { cartItems ->
                    val subtotalPrice = calculateSubtotal(cartItems)
                    val shippingPrice = calculateShipping(subtotalPrice)
                    val totalPrice = calculateTotal(subtotalPrice, shippingPrice)

                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            items = cartItems.map { it.toUi() },
                            subtotalPrice = subtotalPrice,
                            shippingPrice = shippingPrice,
                            totalPrice = totalPrice,
                            errorMessage = null
                        )
                    }
                }
        }
    }

    private fun increaseQuantity(productId: String) {
        viewModelScope.launch {
            val currentItem = uiState.value.items
                .firstOrNull { it.productId == productId }
                ?: return@launch

            runCartAction {
                cartRepository.updateQuantity(
                    productId = productId,
                    quantity = currentItem.quantity + 1
                )
            }
        }
    }

    private fun decreaseQuantity(productId: String) {
        viewModelScope.launch {
            val currentItem = uiState.value.items
                .firstOrNull { it.productId == productId }
                ?: return@launch

            val newQuantity = (currentItem.quantity - 1).coerceAtLeast(1)

            runCartAction {
                cartRepository.updateQuantity(
                    productId = productId,
                    quantity = newQuantity
                )
            }
        }
    }

    private fun removeItem(item: CartItemUi) {
        viewModelScope.launch {
            runCartAction(
                onError = {
                    emitEvent(CartUiEvent.ShowMessage("Failed to remove item"))
                }
            ) {
                lastRemovedItem = item
                cartRepository.removeFromCart(item.productId)

                emitEvent(
                    CartUiEvent.ShowUndoRemoveSnackbar(
                        message = "${item.title} removed from cart"
                    )
                )
            }
        }
    }

    private fun undoRemove() {
        viewModelScope.launch {
            val removedItem = lastRemovedItem ?: return@launch

            runCartAction(
                onError = {
                    emitEvent(CartUiEvent.ShowMessage("Failed to restore item"))
                }
            ) {
                cartRepository.addToCart(
                    CartItem(
                        productId = removedItem.productId,
                        title = removedItem.title,
                        brand = removedItem.brand,
                        category = removedItem.category,
                        imageUrl = removedItem.imageUrl,
                        price = removedItem.priceValue,
                        quantity = removedItem.quantity
                    )
                )

                lastRemovedItem = null
                emitEvent(CartUiEvent.ShowMessage("Item restored"))
            }
        }
    }

    private fun showClearCartDialog() {
        _uiState.update { currentState ->
            currentState.copy(isClearCartDialogVisible = true)
        }
    }

    private fun hideClearCartDialog() {
        _uiState.update { currentState ->
            currentState.copy(isClearCartDialogVisible = false)
        }
    }

    private fun clearCart() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(isClearCartDialogVisible = false)
            }

            runCartAction(
                onError = {
                    emitEvent(CartUiEvent.ShowMessage("Failed to clear cart"))
                }
            ) {
                cartRepository.clearCart()
                lastRemovedItem = null
                emitEvent(CartUiEvent.ShowMessage("Cart cleared"))
            }
        }
    }

    private fun checkout() {
        viewModelScope.launch {
            if (uiState.value.items.isEmpty()) {
                emitEvent(CartUiEvent.ShowMessage("Your cart is empty"))
            } else {
                emitEvent(CartUiEvent.NavigateToCheckout)
            }
        }
    }

    private fun navigateToHome() {
        viewModelScope.launch {
            emitEvent(CartUiEvent.NavigateToHome)
        }
    }

    private suspend fun runCartAction(
        onError: (suspend () -> Unit)? = null,
        action: suspend () -> Unit
    ) {
        try {
            action()
        } catch (_: Throwable) {
            onError?.invoke()
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isLoading = isLoading)
        }
    }

    private fun clearError() {
        _uiState.update { currentState ->
            currentState.copy(errorMessage = null)
        }
    }

    private fun handleStateError(message: String) {
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = false,
                errorMessage = message
            )
        }
    }

    private suspend fun emitEvent(event: CartUiEvent) {
        _events.emit(event)
    }

    private fun calculateSubtotal(items: List<CartItem>): Int {
        return items.sumOf { item -> item.price * item.quantity }
    }

    private fun calculateShipping(subtotal: Int): Int {
        return if (subtotal >= CartUiState.FREE_SHIPPING_THRESHOLD) {
            0
        } else {
            CartUiState.DEFAULT_SHIPPING_PRICE
        }
    }

    private fun calculateTotal(subtotal: Int, shipping: Int): Int {
        return subtotal + shipping
    }

    private fun CartItem.toUi(): CartItemUi {
        return CartItemUi(
            productId = productId,
            title = title,
            brand = brand,
            category = category,
            priceText = PriceFormatter.formatWon(price),
            priceValue = price,
            imageUrl = imageUrl,
            quantity = quantity
        )
    }
}