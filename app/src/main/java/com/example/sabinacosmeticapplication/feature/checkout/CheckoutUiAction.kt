package com.example.sabinacosmeticapplication.feature.checkout

sealed interface CheckoutUiAction {
    data object PlaceOrderClick : CheckoutUiAction
    data object ContinueShoppingClick : CheckoutUiAction
    data object ViewOrdersClick : CheckoutUiAction
}