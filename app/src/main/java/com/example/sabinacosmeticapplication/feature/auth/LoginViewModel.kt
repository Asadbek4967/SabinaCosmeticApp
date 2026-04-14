package com.example.sabinacosmeticapplication.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _events = Channel<LoginUiEvent>(Channel.BUFFERED)
    val events: ReceiveChannel<LoginUiEvent> = _events

    fun onAction(action: LoginUiAction) {
        when (action) {
            is LoginUiAction.EmailChanged -> {
                _uiState.value = _uiState.value.copy(
                    email = action.value,
                    errorMessage = null
                )
            }

            is LoginUiAction.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(
                    password = action.value,
                    errorMessage = null
                )
            }

            LoginUiAction.TogglePasswordVisibility -> {
                _uiState.value = _uiState.value.copy(
                    isPasswordVisible = !_uiState.value.isPasswordVisible
                )
            }

            LoginUiAction.LoginClick -> login()
        }
    }

    private fun login() {
        val email = _uiState.value.email.trim()
        val password = _uiState.value.password.trim()

        when {
            email.isBlank() -> {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Email kiriting"
                )
                return
            }

            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Email format noto‘g‘ri"
                )
                return
            }

            password.isBlank() -> {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Password kiriting"
                )
                return
            }

            password.length < 6 -> {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Password kamida 6 ta belgidan iborat bo‘lsin"
                )
                return
            }
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            loginUseCase(email, password)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = null
                    )
                    _events.send(LoginUiEvent.LoginSuccess)
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = throwable.message ?: "Login failed"
                    )
                }
        }
    }
}