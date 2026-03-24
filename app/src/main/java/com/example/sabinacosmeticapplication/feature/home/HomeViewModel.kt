package com.example.sabinacosmeticapplication.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.domain.usecase.product.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            runCatching {
                getAllProductsUseCase()
            }.onSuccess { products ->
                _uiState.value = HomeUiState(
                    isLoading = false,
                    allProducts = products,
                    errorMessage = null
                )
            }.onFailure { throwable ->
                _uiState.value = HomeUiState(
                    isLoading = false,
                    allProducts = emptyList(),
                    errorMessage = throwable.message ?: "Unknown error occurred"
                )
            }
        }
    }
}