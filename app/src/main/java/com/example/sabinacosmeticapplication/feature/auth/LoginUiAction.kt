package com.example.sabinacosmeticapplication.feature.auth

sealed interface LoginUiAction {
    data class EmailChanged(val value: String) : LoginUiAction
    data class PasswordChanged(val value: String) : LoginUiAction
    data object TogglePasswordVisibility : LoginUiAction
    data object LoginClick : LoginUiAction
}