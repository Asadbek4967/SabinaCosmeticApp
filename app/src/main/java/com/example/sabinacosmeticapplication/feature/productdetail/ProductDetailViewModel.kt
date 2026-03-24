package com.example.sabinacosmeticapplication.feature.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.data.mapper.toCartItem
import com.example.sabinacosmeticapplication.domain.usecase.cart.AddToCartUseCase
import com.example.sabinacosmeticapplication.domain.usecase.product.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState(isLoading = true))
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    private val productId: String = checkNotNull(savedStateHandle["productId"])

    init {
        loadProduct()
    }

    fun loadProduct() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            runCatching {
                getProductByIdUseCase(productId)
            }.onSuccess { product ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    product = product,
                    errorMessage = null
                )
            }.onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    product = null,
                    errorMessage = throwable.message ?: "Failed to load product"
                )
            }
        }
    }

    fun addToCart() {
        val product = _uiState.value.product ?: return

        viewModelScope.launch {
            addToCartUseCase(product.toCartItem())
            _uiState.value = _uiState.value.copy(isAddedToCart = true)
        }
    }

    fun consumeAddedToCartState() {
        _uiState.value = _uiState.value.copy(isAddedToCart = false)
    }
}