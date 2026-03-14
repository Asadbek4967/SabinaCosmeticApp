package com.example.sabinacosmeticapplication.feature.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.sabinacosmeticapplication.data.repository.FakeProductRepository
import com.example.sabinacosmeticapplication.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productRepository: ProductRepository = FakeProductRepository()
    private val productId: String = savedStateHandle["productId"] ?: ""

    private val _uiState = MutableStateFlow(
        ProductDetailUiState(
            product = productRepository.getProductById(productId)
        )
    )
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()
}