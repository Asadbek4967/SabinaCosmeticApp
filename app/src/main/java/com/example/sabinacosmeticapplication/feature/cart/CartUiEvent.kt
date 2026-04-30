package com.example.sabinacosmeticapplication.feature.cart

sealed interface CartUiEvent {

    data class ShowUndoRemoveSnackbar(
        val message: String,
        val actionLabel: String = "Undo"
    ) : CartUiEvent

    data class ShowMessage(
        val message: String
    ) : CartUiEvent

    data object NavigateToCheckout : CartUiEvent
    data object NavigateToHome : CartUiEvent
}