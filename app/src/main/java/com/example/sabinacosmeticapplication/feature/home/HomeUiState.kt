package com.example.sabinacosmeticapplication.feature.home

import androidx.compose.ui.graphics.Color
import com.example.sabinacosmeticapplication.data.model.Product

data class HomeUiState(
    val isLoading: Boolean = false,
    val allProducts: List<Product> = emptyList(),
    val errorMessage: String? = null
) {
    val banners: List<PromoBannerUi>
        get() = promoBanners

    val categories: List<CategoryUi>
        get() = buildCategories(allProducts)

    val flashSaleProducts: List<Product>
        get() = allProducts.filter { it.isFlashSale }

    val bestSellerProducts: List<Product>
        get() = allProducts.filter { it.isBestSeller }

    val recommendedProducts: List<Product>
        get() = allProducts.take(6)
}

data class PromoBannerUi(
    val title: String,
    val subtitle: String,
    val colors: List<Color>
)

data class CategoryUi(
    val title: String,
    val iconEmoji: String
)

private val promoBanners = listOf(
    PromoBannerUi(
        title = "Glow Essentials",
        subtitle = "Refresh your skincare routine with trending Korean beauty picks.",
        colors = listOf(Color(0xFF4D6BFE), Color(0xFF7B8CFF))
    ),
    PromoBannerUi(
        title = "Flash Beauty Sale",
        subtitle = "Limited-time offers on your favorite products.",
        colors = listOf(Color(0xFFFF6B6B), Color(0xFFFF9F7A))
    ),
    PromoBannerUi(
        title = "Best Seller Week",
        subtitle = "Top-rated cosmetic products loved by customers.",
        colors = listOf(Color(0xFF7A5AF8), Color(0xFF9B8AFB))
    )
)

private fun buildCategories(products: List<Product>): List<CategoryUi> {
    val iconMap = mapOf(
        "Skin Care" to "🧴",
        "Skincare" to "🧴",
        "Makeup" to "💄",
        "Hair" to "💇",
        "Hair Care" to "💇",
        "Perfume" to "🌸",
        "Fragrance" to "🌸"
    )

    return products
        .map { it.category.trim() }
        .filter { it.isNotBlank() }
        .distinct()
        .map { category ->
            CategoryUi(
                title = category,
                iconEmoji = iconMap[category] ?: "✨"
            )
        }
}