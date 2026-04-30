package com.example.sabinacosmeticapplication.core.security

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _accessToken = MutableStateFlow(
        prefs.getString(KEY_ACCESS_TOKEN, null)?.trim().takeUnless { it.isNullOrBlank() }
    )
    val accessTokenFlow: StateFlow<String?> = _accessToken.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(!_accessToken.value.isNullOrBlank())
    val isLoggedInFlow: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _sessionEvents = MutableSharedFlow<SessionEvent>(extraBufferCapacity = 1)
    val sessionEvents: SharedFlow<SessionEvent> = _sessionEvents.asSharedFlow()

    fun saveAccessToken(token: String) {
        val normalizedToken = token.trim()
        require(normalizedToken.isNotBlank()) { "Access token bo‘sh bo‘lishi mumkin emas" }

        prefs.edit()
            .putString(KEY_ACCESS_TOKEN, normalizedToken)
            .apply()

        _accessToken.value = normalizedToken
        _isLoggedIn.value = true
    }

    fun getAccessToken(): String? = _accessToken.value

    fun hasSession(): Boolean = !_accessToken.value.isNullOrBlank()

    fun clearSession() {
        prefs.edit().clear().apply()
        _accessToken.value = null
        _isLoggedIn.value = false
    }

    fun clearSessionByUnauthorized() {
        val hadSession = hasSession()
        clearSession()

        if (hadSession) {
            _sessionEvents.tryEmit(SessionEvent.SessionExpired)
        }
    }

    companion object {
        private const val PREFS_NAME = "auth_prefs"
        private const val KEY_ACCESS_TOKEN = "access_token"
    }
}