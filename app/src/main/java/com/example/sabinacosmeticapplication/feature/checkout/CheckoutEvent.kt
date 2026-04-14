package com.example.sabinacosmeticapplication.feature.checkout

sealed interface CheckoutEvent {
    data class ShowError(val message: String) : CheckoutEvent
    data object OrderPlaced : CheckoutEvent
}