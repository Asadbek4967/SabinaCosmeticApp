package com.example.sabinacosmeticapplication.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.data.mapper.toCartItem
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.domain.repository.FavoriteRepository
import com.example.sabinacosmeticapplication.domain.usecase.cart.AddToCartUseCase
import com.example.sabinacosmeticapplication.domain.usecase.product.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val favoriteRepository: FavoriteRepository,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            runCatching {
                val allProducts = getAllProductsUseCase()

                favoriteRepository.observeFavoriteIds().collectLatest { favoriteIds ->
                    val favoriteProducts = allProducts.filter { product ->
                        favoriteIds.contains(product.id)
                    }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        products = favoriteProducts,
                        errorMessage = null
                    )
                }
            }.onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    products = emptyList(),
                    errorMessage = throwable.message ?: "Failed to load favorites"
                )
            }
        }
    }

    fun removeFavorite(productId: String) {
        viewModelScope.launch {
            favoriteRepository.removeFavorite(productId)

            _uiState.value = _uiState.value.copy(
                userMessage = "Removed from favorites"
            )
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            addToCartUseCase(product.toCartItem())

            _uiState.value = _uiState.value.copy(
                userMessage = "Added to cart"
            )
        }
    }

    fun consumeUserMessage() {
        _uiState.value = _uiState.value.copy(
            userMessage = null
        )
    }
}