package com.example.sabinacosmeticapplication.feature.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.data.mapper.toCartItem
import com.example.sabinacosmeticapplication.domain.repository.FavoriteRepository
import com.example.sabinacosmeticapplication.domain.usecase.cart.AddToCartUseCase
import com.example.sabinacosmeticapplication.domain.usecase.product.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val productId: String = checkNotNull(savedStateHandle["productId"])

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ProductDetailUiEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<ProductDetailUiEvent> = _events.asSharedFlow()

    init {
        observeFavoriteState()
        loadProduct()
    }

    fun onAction(action: ProductDetailUiAction) {
        when (action) {
            ProductDetailUiAction.AddToCartClick -> addToCart()
            ProductDetailUiAction.ToggleFavoriteClick -> toggleFavorite()
            ProductDetailUiAction.ToggleDescriptionClick -> toggleDescription()
            ProductDetailUiAction.IncreaseQuantityClick -> increaseQuantity()
            ProductDetailUiAction.DecreaseQuantityClick -> decreaseQuantity()
            ProductDetailUiAction.RetryClick -> loadProduct()
        }
    }

    private fun observeFavoriteState() {
        viewModelScope.launch {
            favoriteRepository.observeIsFavorite(productId).collectLatest { isFavorite ->
                _uiState.update { currentState ->
                    currentState.copy(isFavorite = isFavorite)
                }
            }
        }
    }

    private fun loadProduct() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    product = null,
                    errorMessage = null,
                    quantity = 1,
                    isDescriptionExpanded = false
                )
            }

            runCatching {
                getProductByIdUseCase(productId)
            }.onSuccess { product ->
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        product = product,
                        errorMessage = null
                    )
                }
            }.onFailure { throwable ->
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        product = null,
                        errorMessage = throwable.message ?: "Failed to load product details"
                    )
                }
                emitMessage(throwable.message ?: "Failed to load product details")
            }
        }
    }

    private fun addToCart() {
        val currentState = _uiState.value
        val product = currentState.product ?: return
        val selectedQuantity = currentState.safeQuantity

        viewModelScope.launch {
            runCatching {
                addToCartUseCase(
                    product.toCartItem(quantity = selectedQuantity)
                )
            }.onSuccess {
                emitMessage(
                    if (selectedQuantity == 1) {
                        "Product added to cart"
                    } else {
                        "$selectedQuantity items added to cart"
                    }
                )
            }.onFailure { throwable ->
                emitMessage(throwable.message ?: "Failed to add product to cart")
            }
        }
    }

    private fun toggleFavorite() {
        val currentState = _uiState.value
        val product = currentState.product ?: return
        val currentlyFavorite = currentState.isFavorite

        viewModelScope.launch {
            runCatching {
                if (currentlyFavorite) {
                    favoriteRepository.removeFavorite(product.id)
                } else {
                    favoriteRepository.addFavorite(product.id)
                }
            }.onSuccess {
                emitMessage(
                    if (currentlyFavorite) {
                        "Removed from favorites"
                    } else {
                        "Added to favorites"
                    }
                )
            }.onFailure { throwable ->
                emitMessage(throwable.message ?: "Failed to update favorites")
            }
        }
    }

    private fun toggleDescription() {
        _uiState.update { currentState ->
            currentState.copy(
                isDescriptionExpanded = !currentState.isDescriptionExpanded
            )
        }
    }

    private fun increaseQuantity() {
        _uiState.update { currentState ->
            currentState.copy(
                quantity = currentState.safeQuantity + 1
            )
        }
    }

    private fun decreaseQuantity() {
        _uiState.update { currentState ->
            currentState.copy(
                quantity = (currentState.safeQuantity - 1).coerceAtLeast(1)
            )
        }
    }

    private suspend fun emitMessage(message: String) {
        _events.emit(ProductDetailUiEvent.ShowMessage(message))
    }
}