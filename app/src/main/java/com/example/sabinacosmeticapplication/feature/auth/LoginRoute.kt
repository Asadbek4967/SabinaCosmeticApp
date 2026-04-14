package com.example.sabinacosmeticapplication.feature.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        for (event in viewModel.events) {
            when (event) {
                LoginUiEvent.LoginSuccess -> onLoginSuccess()
            }
        }
    }

    LoginScreen(
        state = state,
        onAction = viewModel::onAction
    )
}