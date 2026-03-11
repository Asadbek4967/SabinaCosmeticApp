package com.example.sabinacosmeticapplication.feature.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.sabinacosmeticapplication.data.repository.FakeProductRepository
import com.example.sabinacosmeticapplication.data.repository.ProductRepository
import com.example.sabinacosmeticapplication.feature.cart.CartManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val repository: ProductRepository = FakeProductRepository()
    private val productId: String = savedStateHandle["productId"] ?: ""

    private val _uiState = MutableStateFlow(
        ProductDetailUiState(
            product = repository.getProductById(productId)
        )
    )
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun addToCart() {
        val product = _uiState.value.product ?: return
        CartManager.repository.addToCart(product)
    }
}