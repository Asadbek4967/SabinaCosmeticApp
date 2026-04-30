package com.example.sabinacosmeticapplication.feature.my

sealed interface MyUiAction {
    data object WishlistClick : MyUiAction
    data object OrdersClick : MyUiAction
    data object LogoutClick : MyUiAction
    data object LogoutDismiss : MyUiAction
    data object LogoutConfirm : MyUiAction
}