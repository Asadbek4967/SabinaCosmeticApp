package com.example.sabinacosmeticapplication.feature.auth

sealed interface LoginUiEvent {
    data object LoginSuccess : LoginUiEvent
}