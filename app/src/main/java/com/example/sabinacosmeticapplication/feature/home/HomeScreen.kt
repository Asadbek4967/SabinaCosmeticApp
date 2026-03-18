package com.example.sabinacosmeticapplication.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.ui.components.AppSectionTitle
import com.example.sabinacosmeticapplication.ui.components.VerticalProductCard
import com.example.sabinacosmeticapplication.ui.theme.AppDimens

private val HomeBackground = Color(0xFFF6F7FB)
private val HomePrimary = Color(0xFF4D6BFE)
private val HomeSoftBlue = Color(0xFFEFF3FF)
private val HomeTextPrimary = Color(0xFF1D2433)
private val HomeTextSecondary = Color(0xFF7C8799)
private val HomeIndicatorInactive = Color(0xFFD7DCE8)

@Composable
fun HomeScreen(
    padding: PaddingValues,
    uiState: HomeUiState,
    onAction: (HomeUiAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackground)
            .padding(padding),
        verticalArrangement = Arrangement.spacedBy(AppDimens.HomeSectionSpacing),
        contentPadding = PaddingValues(bottom = AppDimens.Space28)
    ) {
        item {
            HomeTopSection(
                banners = uiState.banners,
                onSearchClick = { onAction(HomeUiAction.SearchClick) }
            )
        }

        if (uiState.categories.isNotEmpty()) {
            item {
                CategorySection(categories = uiState.categories)
            }
        }

        if (uiState.flashSaleProducts.isNotEmpty()) {
            item {
                ProductShowcaseSection(
                    title = "Flash Sale",
                    subtitle = "Limited-time hot deals",
                    products = uiState.flashSaleProducts,
                    onProductClick = { productId ->
                        onAction(HomeUiAction.ProductClick(productId))
                    }
                )
            }
        }

        if (uiState.bestSellerProducts.isNotEmpty()) {
            item {
                ProductShowcaseSection(
                    title = "Best Sellers",
                    subtitle = "Most loved by customers",
                    products = uiState.bestSellerProducts,
                    onProductClick = { productId ->
                        onAction(HomeUiAction.ProductClick(productId))
                    }
                )
            }
        }

        if (uiState.recommendedProducts.isNotEmpty()) {
            item {
                AppSectionTitle(
                    title = "Recommended for you",
                    subtitle = "Picked for your beauty routine",
                    titleColor = HomeTextPrimary,
                    subtitleColor = HomeTextSecondary,
                    modifier = Modifier.padding(horizontal = AppDimens.ScreenHorizontal)
                )
            }

            item {
                RecommendedGridSection(
                    products = uiState.recommendedProducts,
                    onProductClick = { productId ->
                        onAction(HomeUiAction.ProductClick(productId))
                    }
                )
            }
        }
    }
}

@Composable
private fun HomeTopSection(
    banners: List<PromoBannerUi>,
    onSearchClick: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { banners.size })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(HomeBackground)
            .padding(
                horizontal = AppDimens.ScreenHorizontal,
                vertical = AppDimens.Space14
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppDimens.Space8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Sabina Cosmetic",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = HomeTextPrimary
                )

                Spacer(modifier = Modifier.height(AppDimens.Space4))

                Text(
                    text = "Korean beauty marketplace",
                    style = MaterialTheme.typography.bodyMedium,
                    color = HomeTextSecondary
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(AppDimens.Space10),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.NotificationsNone,
                    contentDescription = null,
                    tint = HomeTextPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(AppDimens.Space16))

        SearchBarFake(onClick = onSearchClick)

        if (banners.isNotEmpty()) {
            Spacer(modifier = Modifier.height(AppDimens.Space16))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                PromoBanner(banner = banners[page])
            }

            Spacer(modifier = Modifier.height(AppDimens.Space10))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(banners.size) { index ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = AppDimens.Space4)
                            .height(AppDimens.HomeBannerIndicatorHeight)
                            .width(
                                if (pagerState.currentPage == index) {
                                    AppDimens.HomeBannerIndicatorActiveWidth
                                } else {
                                    AppDimens.HomeBannerIndicatorInactiveWidth
                                }
                            )
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (pagerState.currentPage == index) HomePrimary
                                else HomeIndicatorInactive
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchBarFake(
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(AppDimens.HomeSearchBarCornerRadius))
            .clickable(onClick = onClick),
        color = Color.White,
        shadowElevation = AppDimens.Space2
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppDimens.Space14,
                    vertical = AppDimens.Space14
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = HomeTextSecondary
            )

            Spacer(modifier = Modifier.width(AppDimens.Space10))

            Text(
                text = "Search skincare, serum, cream...",
                color = HomeTextSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun PromoBanner(
    banner: PromoBannerUi
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(AppDimens.HomeBannerCornerRadius))
            .background(brush = Brush.horizontalGradient(banner.colors))
            .padding(AppDimens.Space20)
    ) {
        Column {
            Text(
                text = banner.title,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(AppDimens.Space8))

            Text(
                text = banner.subtitle,
                color = Color.White.copy(alpha = 0.92f),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(AppDimens.Space14))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.White.copy(alpha = 0.18f))
                    .padding(
                        horizontal = AppDimens.Space14,
                        vertical = AppDimens.Space8
                    )
            ) {
                Text(
                    text = "Shop now",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun CategorySection(
    categories: List<CategoryUi>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
    ) {
        AppSectionTitle(
            title = "Categories",
            subtitle = "Browse by type",
            titleColor = HomeTextPrimary,
            subtitleColor = HomeTextSecondary,
            modifier = Modifier.padding(horizontal = AppDimens.ScreenHorizontal)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = AppDimens.ScreenHorizontal),
            horizontalArrangement = Arrangement.spacedBy(AppDimens.Space12)
        ) {
            items(
                items = categories,
                key = { it.title }
            ) { category ->
                Surface(
                    shape = RoundedCornerShape(AppDimens.HomeCategoryCardCornerRadius),
                    color = Color.White,
                    tonalElevation = AppDimens.Space2
                ) {
                    Column(
                        modifier = Modifier.padding(
                            horizontal = AppDimens.Space16,
                            vertical = AppDimens.Space12
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(HomeSoftBlue)
                                .padding(AppDimens.Space12),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = category.iconEmoji)
                        }

                        Spacer(modifier = Modifier.height(AppDimens.Space8))

                        Text(
                            text = category.title,
                            color = HomeTextPrimary,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductShowcaseSection(
    title: String,
    subtitle: String,
    products: List<Product>,
    onProductClick: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
    ) {
        AppSectionTitle(
            title = title,
            subtitle = subtitle,
            titleColor = HomeTextPrimary,
            subtitleColor = HomeTextSecondary,
            modifier = Modifier.padding(horizontal = AppDimens.ScreenHorizontal)
        )

        HorizontalProductSection(
            products = products,
            onProductClick = onProductClick
        )
    }
}

@Composable
private fun HorizontalProductSection(
    products: List<Product>,
    onProductClick: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = AppDimens.ScreenHorizontal),
        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space12)
    ) {
        items(
            items = products,
            key = { it.id }
        ) { product ->
            Box(
                modifier = Modifier.width(AppDimens.HomeHorizontalCardWidth)
            ) {
                VerticalProductCard(
                    product = product,
                    onClick = { onProductClick(product.id) }
                )
            }
        }
    }
}

@Composable
private fun RecommendedGridSection(
    products: List<Product>,
    onProductClick: (String) -> Unit
) {
    val safeProducts = products.take(AppDimens.HomeRecommendedMaxItems)

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(AppDimens.HomeRecommendedGridHeight)
            .padding(horizontal = AppDimens.ScreenHorizontal),
        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space12),
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space12),
        userScrollEnabled = false
    ) {
        items(
            items = safeProducts,
            key = { it.id }
        ) { product ->
            VerticalProductCard(
                product = product,
                onClick = { onProductClick(product.id) }
            )
        }
    }
}