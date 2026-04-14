package com.example.sabinacosmeticapplication.feature.home

import androidx.compose.ui.graphics.Color
import com.example.sabinacosmeticapplication.data.model.Product

data class HomeUiState(
    val isLoading: Boolean = false,
    val allProducts: List<Product> = emptyList(),
    val errorMessage: String? = null
) {
    private val uniqueProducts: List<Product>
        get() = allProducts.distinctBy { it.id }

    val banners: List<PromoBannerUi>
        get() = promoBanners

    val categories: List<CategoryUi>
        get() = buildHomeCategories(uniqueProducts)

    val featuredProducts: List<Product>
        get() {
            val prioritized = uniqueProducts
                .sortedWith(
                    compareByDescending<Product> { it.isBestSeller }
                        .thenByDescending { it.isFlashSale }
                        .thenByDescending { it.priceValue }
                )

            return prioritized.take(FEATURED_MAX_ITEMS)
        }

    val flashSaleProducts: List<Product>
        get() {
            val excludedIds = featuredProducts.map { it.id }.toSet()

            return uniqueProducts
                .filter { it.isFlashSale && it.id !in excludedIds }
                .take(FLASH_SALE_MAX_ITEMS)
        }

    val bestSellerProducts: List<Product>
        get() {
            val excludedIds = (featuredProducts + flashSaleProducts)
                .map { it.id }
                .toSet()

            return uniqueProducts
                .filter { it.isBestSeller && it.id !in excludedIds }
                .take(BEST_SELLER_MAX_ITEMS)
        }

    val recommendedProducts: List<Product>
        get() {
            val excludedIds = (featuredProducts + flashSaleProducts + bestSellerProducts)
                .map { it.id }
                .toSet()

            val remainder = uniqueProducts
                .filterNot { it.id in excludedIds }

            return remainder.take(RECOMMENDED_MAX_ITEMS)
        }

    val hasProducts: Boolean
        get() = uniqueProducts.isNotEmpty()

    val isEmptyData: Boolean
        get() = uniqueProducts.isEmpty()

    val showContent: Boolean
        get() = !isLoading && errorMessage == null && hasProducts

    val showError: Boolean
        get() = !isLoading && !errorMessage.isNullOrBlank()

    val showEmpty: Boolean
        get() = !isLoading && errorMessage == null && isEmptyData

    companion object {
        private const val FEATURED_MAX_ITEMS = 6
        private const val FLASH_SALE_MAX_ITEMS = 6
        private const val BEST_SELLER_MAX_ITEMS = 6
        private const val RECOMMENDED_MAX_ITEMS = 8
    }
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

private fun buildHomeCategories(products: List<Product>): List<CategoryUi> {
    return products
        .map { it.category.trim() }
        .filter { it.isNotBlank() }
        .distinct()
        .take(6)
        .map { category ->
            CategoryUi(
                title = category,
                iconEmoji = resolveCategoryEmoji(category)
            )
        }
}

private fun resolveCategoryEmoji(category: String): String {
    return when (category.trim().lowercase()) {
        "skin care", "skincare", "cleanser", "toner", "serum", "cream", "sunscreen", "mask pack", "eye & lip care" -> "🧴"
        "hair care", "shampoo", "conditioner", "scalp care" -> "💧"
        "sun care", "sunscreen" -> "☀️"
        "makeup", "lip makeup", "base makeup", "eye makeup" -> "💄"
        "body care", "body wash", "body lotion", "hand care", "foot care" -> "🫧"
        "vitamins", "wellness", "probiotics", "collagen", "biotin" -> "✨"
        else -> "🌿"
    }
}