package com.example.sabinacosmeticapplication.feature.checkout

sealed interface CheckoutUiEvent {
    data class ShowMessage(val message: String) : CheckoutUiEvent
    data object NavigateToHome : CheckoutUiEvent
    data object NavigateToOrders : CheckoutUiEvent
}