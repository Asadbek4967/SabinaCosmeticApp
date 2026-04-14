package com.example.sabinacosmeticapplication.feature.my

sealed interface MyUiEvent {
    data object NavigateToWishlist : MyUiEvent
    data object NavigateToOrders : MyUiEvent
    data object LogoutCompleted : MyUiEvent
}