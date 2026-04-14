package com.example.sabinacosmeticapplication.feature.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MyViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyUiState())
    val uiState: StateFlow<MyUiState> = _uiState.asStateFlow()

    private val _events = Channel<MyUiEvent>(Channel.BUFFERED)
    val events: ReceiveChannel<MyUiEvent> = _events

    fun onAction(action: MyUiAction) {
        when (action) {
            MyUiAction.WishlistClick -> {
                viewModelScope.launch {
                    _events.send(MyUiEvent.NavigateToWishlist)
                }
            }

            MyUiAction.OrdersClick -> {
                viewModelScope.launch {
                    _events.send(MyUiEvent.NavigateToOrders)
                }
            }

            MyUiAction.LogoutClick -> {
                _uiState.value = _uiState.value.copy(
                    isLogoutDialogVisible = true
                )
            }

            MyUiAction.LogoutDismiss -> {
                _uiState.value = _uiState.value.copy(
                    isLogoutDialogVisible = false
                )
            }

            MyUiAction.LogoutConfirm -> {
                logout()
            }
        }
    }

    private fun logout() {
        if (_uiState.value.isLoggingOut) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoggingOut = true
            )

            runCatching {
                authRepository.logout()
            }.onSuccess {
                _uiState.value = _uiState.value.copy(
                    isLoggingOut = false,
                    isLogoutDialogVisible = false
                )
                _events.send(MyUiEvent.LogoutCompleted)
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    isLoggingOut = false,
                    isLogoutDialogVisible = false
                )
                _events.send(MyUiEvent.LogoutCompleted)
            }
        }
    }
}