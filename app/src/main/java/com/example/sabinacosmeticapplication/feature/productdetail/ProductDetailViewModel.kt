package com.example.sabinacosmeticapplication.feature.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.domain.usecase.AddToCartUseCase
import com.example.sabinacosmeticapplication.domain.usecase.GetProductByIdUseCase
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

    private val productId: String = checkNotNull(savedStateHandle["productId"])

    private val _uiState = MutableStateFlow(
        ProductDetailUiState(isLoading = true)
    )
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    init {
        loadProduct()
    }

    private fun loadProduct() {
        val product = getProductByIdUseCase(productId)

        _uiState.value = if (product != null) {
            ProductDetailUiState(
                isLoading = false,
                product = product,
                isAddedToCart = false,
                errorMessage = null
            )
        } else {
            ProductDetailUiState(
                isLoading = false,
                product = null,
                isAddedToCart = false,
                errorMessage = "Product topilmadi"
            )
        }
    }

    fun addToCart() {
        val currentProduct = _uiState.value.product ?: return

        viewModelScope.launch {
            addToCartUseCase(currentProduct.id)
            _uiState.value = _uiState.value.copy(
                isAddedToCart = true
            )
        }
    }

    fun consumeAddedToCartState() {
        if (_uiState.value.isAddedToCart) {
            _uiState.value = _uiState.value.copy(
                isAddedToCart = false
            )
        }
    }
}