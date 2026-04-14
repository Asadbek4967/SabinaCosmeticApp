package com.example.sabinacosmeticapplication.core.security

sealed interface SessionEvent {
    data object SessionExpired : SessionEvent
}