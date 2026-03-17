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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.ui.components.AppSectionTitle
import com.example.sabinacosmeticapplication.ui.components.VerticalProductCard

private val HomeBackground = Color(0xFFF6F7FB)
private val HomePrimary = Color(0xFF4D6BFE)
private val HomeSoftBlue = Color(0xFFEFF3FF)
private val HomeTextPrimary = Color(0xFF1D2433)
private val HomeTextSecondary = Color(0xFF7C8799)

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
            .padding(padding)
            .navigationBarsPadding()
    ) {
        item {
            HomeTopSection(
                banners = uiState.banners,
                onSearchClick = {
                    onAction(HomeUiAction.SearchClick)
                }
            )
        }

        if (uiState.categories.isNotEmpty()) {
            item {
                CategorySection(categories = uiState.categories)
            }
        }

        if (uiState.flashSaleProducts.isNotEmpty()) {
            item {
                AppSectionTitle(
                    title = "Flash Sale",
                    subtitle = "Limited-time hot deals",
                    titleColor = HomeTextPrimary,
                    subtitleColor = HomeTextSecondary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item {
                HorizontalProductSection(
                    products = uiState.flashSaleProducts,
                    onProductClick = { productId ->
                        onAction(HomeUiAction.ProductClick(productId))
                    }
                )
            }
        }

        if (uiState.bestSellerProducts.isNotEmpty()) {
            item {
                AppSectionTitle(
                    title = "Best Sellers",
                    subtitle = "Most loved by customers",
                    titleColor = HomeTextPrimary,
                    subtitleColor = HomeTextSecondary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item {
                HorizontalProductSection(
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
                    modifier = Modifier.padding(horizontal = 16.dp)
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

        item {
            Spacer(modifier = Modifier.height(28.dp))
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
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Sabina Cosmetic",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = HomeTextPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

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
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.NotificationsNone,
                    contentDescription = null,
                    tint = HomeTextPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SearchBarFake(onClick = onSearchClick)

        Spacer(modifier = Modifier.height(16.dp))

        if (banners.isNotEmpty()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                PromoBanner(banner = banners[page])
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(banners.size) { index ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .height(8.dp)
                            .width(if (pagerState.currentPage == index) 22.dp else 8.dp)
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (pagerState.currentPage == index) HomePrimary
                                else Color(0xFFD7DCE8)
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
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = HomeTextSecondary
            )

            Spacer(modifier = Modifier.width(10.dp))

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
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.horizontalGradient(banner.colors)
            )
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = banner.title,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = banner.subtitle,
                color = Color.White.copy(alpha = 0.92f),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.White.copy(alpha = 0.18f))
                    .padding(horizontal = 14.dp, vertical = 8.dp)
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
        modifier = Modifier.padding(top = 18.dp, bottom = 8.dp)
    ) {
        AppSectionTitle(
            title = "Categories",
            subtitle = "Browse by type",
            titleColor = HomeTextPrimary,
            subtitleColor = HomeTextSecondary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    tonalElevation = 1.dp
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(HomeSoftBlue)
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = category.iconEmoji)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

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
private fun HorizontalProductSection(
    products: List<Product>,
    onProductClick: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(products) { product ->
            Box(
                modifier = Modifier.width(210.dp)
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
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .height(((products.size + 1) / 2 * 320).dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        userScrollEnabled = false
    ) {
        items(products) { product ->
            VerticalProductCard(
                product = product,
                onClick = { onProductClick(product.id) }
            )
        }
    }
}