package com.example.sabinacosmeticapplication.feature.categoryproducts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.domain.usecase.product.GetProductsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CategoryProductsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase
) : ViewModel() {

    private val categoryArg = savedStateHandle.get<String>("category").orEmpty()

    private val decodedCategory = URLDecoder.decode(
        categoryArg,
        StandardCharsets.UTF_8.toString()
    )

    private val _uiState = MutableStateFlow(
        CategoryProductsUiState(
            categoryName = decodedCategory,
            products = emptyList(),
            isLoading = true,
            errorMessage = null
        )
    )
    val uiState: StateFlow<CategoryProductsUiState> = _uiState.asStateFlow()

    init {
        loadCategoryProducts()
    }

    fun loadCategoryProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            runCatching {
                getProductsByCategoryUseCase(decodedCategory)
            }.onSuccess { products ->
                _uiState.value = _uiState.value.copy(
                    products = products,
                    isLoading = false,
                    errorMessage = null
                )
            }.onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    products = emptyList(),
                    isLoading = false,
                    errorMessage = throwable.message ?: "Failed to load category products"
                )
            }
        }
    }
}