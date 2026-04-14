package com.example.sabinacosmeticapplication.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.domain.usecase.product.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var loadProductsJob: Job? = null

    init {
        loadProducts(forceRefresh = false)
    }

    fun onAction(action: HomeUiAction) {
        when (action) {
            HomeUiAction.RetryClick -> loadProducts(forceRefresh = true)
            else -> Unit
        }
    }

    fun loadProducts(forceRefresh: Boolean = false) {
        if (loadProductsJob?.isActive == true && !forceRefresh) return

        loadProductsJob?.cancel()
        loadProductsJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            runCatching {
                getAllProductsUseCase()
            }.onSuccess { products ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        allProducts = products.distinctBy { product -> product.id },
                        errorMessage = null
                    )
                }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        allProducts = emptyList(),
                        errorMessage = throwable.userFriendlyMessage()
                    )
                }
            }
        }
    }

    private fun Throwable.userFriendlyMessage(): String {
        val rawMessage = message.orEmpty()

        return when {
            rawMessage.contains("internet", ignoreCase = true) ->
                "Internet connection is unstable. Please try again."

            rawMessage.contains("timeout", ignoreCase = true) ->
                "The server is taking too long to respond. Please try again."

            rawMessage.contains("unable to resolve host", ignoreCase = true) ->
                "Server address could not be reached."

            rawMessage.contains("failed to connect", ignoreCase = true) ->
                "Cannot connect to backend server."

            rawMessage.contains("server error", ignoreCase = true) ->
                rawMessage

            rawMessage.isNotBlank() ->
                rawMessage

            else ->
                "Unable to load products right now. Please try again."
        }
    }
}