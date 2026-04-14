package com.example.sabinacosmeticapplication.core.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.core.security.SessionEvent
import com.example.sabinacosmeticapplication.core.security.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class AppSessionViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppSessionUiState())
    val uiState: StateFlow<AppSessionUiState> = _uiState.asStateFlow()

    private val _events = Channel<SessionEvent>(Channel.BUFFERED)
    val events: ReceiveChannel<SessionEvent> = _events

    init {
        observeSession()
        observeSessionEvents()
    }

    private fun observeSession() {
        viewModelScope.launch {
            tokenManager.isLoggedInFlow.collectLatest { isLoggedIn ->
                _uiState.value = AppSessionUiState(
                    isReady = true,
                    isLoggedIn = isLoggedIn
                )
            }
        }
    }

    private fun observeSessionEvents() {
        viewModelScope.launch {
            tokenManager.sessionEvents.collectLatest { event ->
                _events.send(event)
            }
        }
    }

    fun logout() {
        tokenManager.clearSession()
    }
}