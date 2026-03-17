package com.example.sabinacosmeticapplication.feature.home

import androidx.compose.ui.graphics.Color
import com.example.sabinacosmeticapplication.data.model.Product

data class HomeUiState(
    val banners: List<PromoBannerUi> = emptyList(),
    val categories: List<CategoryUi> = emptyList(),
    val flashSaleProducts: List<Product> = emptyList(),
    val bestSellerProducts: List<Product> = emptyList(),
    val recommendedProducts: List<Product> = emptyList()
)

data class PromoBannerUi(
    val title: String,
    val subtitle: String,
    val colors: List<Color>
)

data class CategoryUi(
    val title: String,
    val iconEmoji: String
)