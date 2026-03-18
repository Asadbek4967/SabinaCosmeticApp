package com.example.sabinacosmeticapplication.feature.categoryproducts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.sabinacosmeticapplication.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class CategoryProductsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: ProductRepository
) : ViewModel() {

    private val categoryArg = savedStateHandle.get<String>("category").orEmpty()
    private val decodedCategory = URLDecoder.decode(
        categoryArg,
        StandardCharsets.UTF_8.toString()
    )

    private val _uiState = MutableStateFlow(
        CategoryProductsUiState(
            categoryName = decodedCategory,
            products = repository.getProductsByCategory(decodedCategory),
            isLoading = false
        )
    )
    val uiState: StateFlow<CategoryProductsUiState> = _uiState.asStateFlow()
}