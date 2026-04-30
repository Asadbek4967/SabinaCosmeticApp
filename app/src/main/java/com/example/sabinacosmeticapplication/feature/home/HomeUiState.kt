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

    // 🔥 CATEGORY (emoji emas, iconName ishlatamiz)
    val categories: List<CategoryUi>
        get() = buildHomeCategories(uniqueProducts)

    // 🔥 FEATURED
    val featuredProducts: List<Product>
        get() = uniqueProducts
            .sortedWith(
                compareByDescending<Product> { it.isBestSeller }
                    .thenByDescending { it.isFlashSale }
                    .thenByDescending { it.priceValue }
            )
            .take(FEATURED_MAX_ITEMS)

    // 🔥 FLASH SALE
    val flashSaleProducts: List<Product>
        get() {
            val excluded = featuredProducts.map { it.id }.toSet()

            return uniqueProducts
                .filter { it.isFlashSale && it.id !in excluded }
                .take(FLASH_SALE_MAX_ITEMS)
        }

    // 🔥 BEST SELLER
    val bestSellerProducts: List<Product>
        get() {
            val excluded = (featuredProducts + flashSaleProducts)
                .map { it.id }
                .toSet()

            return uniqueProducts
                .filter { it.isBestSeller && it.id !in excluded }
                .take(BEST_SELLER_MAX_ITEMS)
        }

    // 🔥 RECOMMENDED
    val recommendedProducts: List<Product>
        get() {
            val excluded = (featuredProducts + flashSaleProducts + bestSellerProducts)
                .map { it.id }
                .toSet()

            return uniqueProducts
                .filterNot { it.id in excluded }
                .take(RECOMMENDED_MAX_ITEMS)
        }

    val hasProducts: Boolean
        get() = uniqueProducts.isNotEmpty()

    val showContent: Boolean
        get() = !isLoading && errorMessage == null && hasProducts

    val showError: Boolean
        get() = !isLoading && !errorMessage.isNullOrBlank()

    val showEmpty: Boolean
        get() = !isLoading && errorMessage == null && uniqueProducts.isEmpty()

    companion object {
        private const val FEATURED_MAX_ITEMS = 6
        private const val FLASH_SALE_MAX_ITEMS = 6
        private const val BEST_SELLER_MAX_ITEMS = 6
        private const val RECOMMENDED_MAX_ITEMS = 8
    }
}

// 🔥 BANNER
data class PromoBannerUi(
    val title: String,
    val subtitle: String,
    val colors: List<Color>
)

// 🔥 CATEGORY (emoji olib tashladik)
data class CategoryUi(
    val title: String,
    val iconName: String
)

// 🔥 STATIC BANNER DATA
private val promoBanners = listOf(
    PromoBannerUi(
        title = "Glow Essentials",
        subtitle = "Trending Korean skincare for glowing skin.",
        colors = listOf(Color(0xFF4D6BFE), Color(0xFF7B8CFF))
    ),
    PromoBannerUi(
        title = "Flash Sale",
        subtitle = "Limited-time beauty deals.",
        colors = listOf(Color(0xFFFF6B6B), Color(0xFFFF9F7A))
    ),
    PromoBannerUi(
        title = "Best Sellers",
        subtitle = "Most loved products by customers.",
        colors = listOf(Color(0xFF7A5AF8), Color(0xFF9B8AFB))
    )
)

// 🔥 CATEGORY BUILD (iconName bilan)
private fun buildHomeCategories(products: List<Product>): List<CategoryUi> {
    return products
        .map { it.category.trim() }
        .filter { it.isNotBlank() }
        .distinct()
        .take(6)
        .map { category ->
            CategoryUi(
                title = category,
                iconName = resolveCategoryIconName(category)
            )
        }
}

// 🔥 ICON LOGIC (resolver bilan mos)
private fun resolveCategoryIconName(category: String): String {
    val key = category.lowercase()

    return when {
        key.contains("skin") || key.contains("face") -> "face"
        key.contains("hair") -> "hair"
        key.contains("makeup") -> "makeup"
        key.contains("body") -> "body"
        key.contains("sun") -> "sun"
        key.contains("vitamin") -> "vitamin"
        else -> "face"
    }
}