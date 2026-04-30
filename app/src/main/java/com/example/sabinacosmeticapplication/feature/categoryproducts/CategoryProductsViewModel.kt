package com.example.sabinacosmeticapplication.feature.categoryproducts

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sabinacosmeticapplication.domain.usecase.category.GetCategoryProductsUseCase
import com.example.sabinacosmeticapplication.feature.categoryproducts.data.CategoryProductResolver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CategoryProductsViewModel @Inject constructor(
    private val getCategoryProductsUseCase: GetCategoryProductsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val categoryId: String =
        savedStateHandle.get<String>("categoryId").orEmpty()

    private val categoryTitle: String =
        savedStateHandle.get<String>("categoryTitle").orEmpty()

    private val profile = CategoryProductResolver.resolve(
        routeCategory = categoryTitle.ifBlank { categoryId }
    )

    private val _uiState = MutableStateFlow(
        CategoryProductsUiState(
            isLoading = true,
            categoryKey = categoryId,
            categoryDisplayName = profile.displayName.ifBlank { "Category" },
            categorySubtitle = profile.subtitle.ifBlank {
                "Explore curated products for your beauty routine."
            },
        ),
    )
    val uiState: StateFlow<CategoryProductsUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun reload() {
        loadProducts()
    }

    private fun loadProducts() {
        Log.d("CategoryProductsVM", "categoryId = $categoryId")
        Log.d("CategoryProductsVM", "categoryTitle = $categoryTitle")

        if (categoryId.isBlank()) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    products = emptyList(),
                    fallbackProducts = emptyList(),
                    isFallbackMode = false,
                    errorMessage = "Category id is missing.",
                    categoryKey = categoryId,
                    categoryDisplayName = profile.displayName.ifBlank { "Category" },
                    categorySubtitle = profile.subtitle.ifBlank {
                        "Explore curated products for your beauty routine."
                    },
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    categoryKey = categoryId,
                    categoryDisplayName = profile.displayName.ifBlank { "Category" },
                    categorySubtitle = profile.subtitle.ifBlank {
                        "Explore curated products for your beauty routine."
                    },
                )
            }

            runCatching {
                getCategoryProductsUseCase(categoryId)
            }.onSuccess { products ->
                Log.d("CategoryProductsVM", "Loaded products count = ${products.size}")

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        products = products,
                        fallbackProducts = emptyList(),
                        isFallbackMode = false,
                        errorMessage = null,
                        categoryKey = categoryId,
                        categoryDisplayName = profile.displayName.ifBlank { "Category" },
                        categorySubtitle = profile.subtitle.ifBlank {
                            "Explore curated products for your beauty routine."
                        },
                    )
                }
            }.onFailure { throwable ->
                Log.e("CategoryProductsVM", "Failed to load category products", throwable)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        products = emptyList(),
                        fallbackProducts = emptyList(),
                        isFallbackMode = false,
                        errorMessage = throwable.message
                            ?: "Failed to load category products.",
                        categoryKey = categoryId,
                        categoryDisplayName = profile.displayName.ifBlank { "Category" },
                        categorySubtitle = profile.subtitle.ifBlank {
                            "Explore curated products for your beauty routine."
                        },
                    )
                }
            }
        }
    }
}