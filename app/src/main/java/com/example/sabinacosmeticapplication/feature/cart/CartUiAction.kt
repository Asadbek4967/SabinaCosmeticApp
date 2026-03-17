package com.example.sabinacosmeticapplication.feature.cart

sealed interface CartUiAction {
    data class IncreaseQuantity(val productId: String) : CartUiAction
    data class DecreaseQuantity(val productId: String) : CartUiAction
    data class RemoveItem(val productId: String) : CartUiAction

    data object OnClearCartClick : CartUiAction
    data object OnClearCartDismiss : CartUiAction
    data object OnClearCartConfirm : CartUiAction

    data object UndoRemove : CartUiAction
    data object CheckoutClick : CartUiAction
}