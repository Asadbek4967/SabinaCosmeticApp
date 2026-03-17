package com.example.sabinacosmeticapplication.feature.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.sabinacosmeticapplication.domain.usecase.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {

    private val allProducts = getAllProductsUseCase()

    private val _uiState = MutableStateFlow(
        HomeUiState(
            banners = listOf(
                PromoBannerUi(
                    title = "Glow up with Korean skincare",
                    subtitle = "Discover trending beauty products with fresh daily deals.",
                    colors = listOf(Color(0xFF5B7CFF), Color(0xFF7FA3FF))
                ),
                PromoBannerUi(
                    title = "Serum week special sale",
                    subtitle = "Brightening, calming, hydrating serums at better prices.",
                    colors = listOf(Color(0xFFFF7A59), Color(0xFFFFB36B))
                ),
                PromoBannerUi(
                    title = "Daily sun care picks",
                    subtitle = "Lightweight SPF products for everyday protection.",
                    colors = listOf(Color(0xFF22A06B), Color(0xFF6FD19B))
                )
            ),
            categories = listOf(
                CategoryUi("Skincare", "🧴"),
                CategoryUi("Serum", "💧"),
                CategoryUi("Sun Care", "☀️"),
                CategoryUi("Cream", "🫙"),
                CategoryUi("Toner", "✨"),
                CategoryUi("Cleanser", "🫧"),
                CategoryUi("Lip Care", "💄"),
                CategoryUi("Ampoule", "🩵")
            ),
            flashSaleProducts = allProducts.filter { it.isFlashSale },
            bestSellerProducts = allProducts.filter { it.isBestSeller },
            recommendedProducts = allProducts
        )
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onAction(action: HomeUiAction) {
        when (action) {
            HomeUiAction.SearchClick -> Unit
            is HomeUiAction.ProductClick -> Unit
        }
    }
}