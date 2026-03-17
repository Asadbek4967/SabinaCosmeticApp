package com.example.sabinacosmeticapplication.feature.cart

sealed interface CartUiEvent {
    data object ShowItemRemovedSnackbar : CartUiEvent
    data object ShowCheckoutNotReadyMessage : CartUiEvent
}