package com.example.sabinacosmeticapplication.feature.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.data.repository.FakeProductRepository
import com.example.sabinacosmeticapplication.data.repository.ProductRepository
import com.example.sabinacosmeticapplication.feature.cart.CartManager
import com.example.sabinacosmeticapplication.feature.cart.CartProduct
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _events = MutableSharedFlow<ProductDetailEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<ProductDetailEvent> = _events.asSharedFlow()

    fun addToCart() {
        val product = _uiState.value.product ?: return

        CartManager.addToCart(product.toCartProduct())

        _events.tryEmit(
            ProductDetailEvent.ShowMessage(
                message = "${product.title} added to cart"
            )
        )
    }
}

sealed class ProductDetailEvent {
    data class ShowMessage(val message: String) : ProductDetailEvent()
}

private fun Product.toCartProduct(): CartProduct {
    return CartProduct(
        id = id,
        title = title,
        brand = brand,
        category = category,
        price = price,
        priceValue = priceValue,
        imageUrl = imageUrl
    )
}