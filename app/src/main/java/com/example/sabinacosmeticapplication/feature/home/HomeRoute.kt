package com.example.sabinacosmeticapplication.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sabinacosmeticapplication.ui.components.common.AppEmptyState
import com.example.sabinacosmeticapplication.ui.components.common.AppErrorState
import com.example.sabinacosmeticapplication.ui.components.common.AppLoadingState

@Composable
fun HomeRoute(
    padding: PaddingValues,
    onSearchClick: () -> Unit,
    onProductClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            uiState.isLoading -> {
                AppLoadingState(padding = padding)
            }

            uiState.errorMessage != null -> {
                AppErrorState(
                    padding = padding,
                    message = uiState.errorMessage ?: "Something went wrong",
                    onRetry = viewModel::loadProducts
                )
            }

            uiState.allProducts.isEmpty() -> {
                AppEmptyState(
                    padding = padding,
                    title = "No products found",
                    message = "Products are currently unavailable. Please try again later."
                )
            }

            else -> {
                HomeScreen(
                    padding = padding,
                    uiState = uiState,
                    onAction = { action ->
                        when (action) {
                            HomeUiAction.SearchClick -> onSearchClick()
                            is HomeUiAction.ProductClick -> onProductClick(action.productId)
                        }
                    }
                )
            }
        }
    }
}