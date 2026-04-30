package com.example.sabinacosmeticapplication.feature.cart

sealed interface CartUiAction {

    data class IncreaseQuantity(val productId: String) : CartUiAction
    data class DecreaseQuantity(val productId: String) : CartUiAction
    data class RemoveItem(val item: CartItemUi) : CartUiAction

    data object ClearCartClicked : CartUiAction
    data object ClearCartDismissed : CartUiAction
    data object ClearCartConfirmed : CartUiAction

    data object UndoRemoveClicked : CartUiAction
    data object CheckoutClicked : CartUiAction
    data object StartShoppingClicked : CartUiAction
}