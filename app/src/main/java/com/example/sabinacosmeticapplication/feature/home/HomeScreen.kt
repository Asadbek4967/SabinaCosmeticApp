package com.example.sabinacosmeticapplication.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.sabinacosmeticapplication.data.model.Product

private val HomeBg = Color(0xFFF6F7FB)
private val PrimaryBlue = Color(0xFF4D6BFE)
private val SoftBlue = Color(0xFFEFF3FF)
private val TextPrimary = Color(0xFF1D2433)
private val TextSecondary = Color(0xFF7C8799)
private val DangerRed = Color(0xFFFF5A5F)

private data class PromoBannerUi(
    val title: String,
    val subtitle: String,
    val colors: List<Color>
)

private data class CategoryUi(
    val title: String,
    val iconEmoji: String
)

@Composable
fun HomeScreen(
    padding: PaddingValues,
    uiState: HomeUiState,
    onSearchClick: () -> Unit,
    onProductClick: (String) -> Unit
) {
    val categories = listOf(
        CategoryUi("Skincare", "🧴"),
        CategoryUi("Serum", "💧"),
        CategoryUi("Sun Care", "☀️"),
        CategoryUi("Cream", "🫙"),
        CategoryUi("Toner", "✨"),
        CategoryUi("Cleanser", "🫧"),
        CategoryUi("Lip Care", "💄"),
        CategoryUi("Ampoule", "🩵")
    )

    val banners = listOf(
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
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBg)
            .padding(padding)
            .navigationBarsPadding()
    ) {
        item {
            HomeTopSection(
                banners = banners,
                onSearchClick = onSearchClick
            )
        }

        item {
            CategorySection(categories = categories)
        }

        if (uiState.flashSaleProducts.isNotEmpty()) {
            item {
                SectionTitle(
                    title = "Flash Sale",
                    subtitle = "Limited-time hot deals"
                )
            }

            item {
                HorizontalProductSection(
                    products = uiState.flashSaleProducts,
                    onProductClick = onProductClick
                )
            }
        }

        if (uiState.bestSellerProducts.isNotEmpty()) {
            item {
                SectionTitle(
                    title = "Best Sellers",
                    subtitle = "Most loved by customers"
                )
            }

            item {
                HorizontalProductSection(
                    products = uiState.bestSellerProducts,
                    onProductClick = onProductClick
                )
            }
        }

        if (uiState.recommendedProducts.isNotEmpty()) {
            item {
                SectionTitle(
                    title = "Recommended for you",
                    subtitle = "Picked for your beauty routine"
                )
            }

            item {
                RecommendedGridSection(
                    products = uiState.recommendedProducts,
                    onProductClick = onProductClick
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
            .background(HomeBg)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Sabina Cosmetic",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Korean beauty marketplace",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.NotificationsNone,
                    contentDescription = null,
                    tint = TextPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SearchBarFake(onClick = onSearchClick)

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            PromoBanner(banner = banners[page])
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(banners.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(
                            width = if (pagerState.currentPage == index) 22.dp else 8.dp,
                            height = 8.dp
                        )
                        .clip(RoundedCornerShape(50))
                        .background(
                            if (pagerState.currentPage == index) PrimaryBlue
                            else Color(0xFFD7DCE8)
                        )
                )
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
                tint = TextSecondary
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Search skincare, serum, cream...",
                color = TextSecondary,
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
                brush = Brush.horizontalGradient(colors = banner.colors)
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
        SectionTitle(
            title = "Categories",
            subtitle = "Browse by type"
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories.size) { index ->
                val category = categories[index]
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
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(SoftBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = category.iconEmoji)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = category.title,
                            color = TextPrimary,
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
private fun SectionTitle(
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
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
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(products.size) { index ->
            val product = products[index]
            Box(modifier = Modifier.width(210.dp)) {
                ProductCard(
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
            ProductCard(
                product = product,
                onClick = { onProductClick(product.id) }
            )
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(18.dp))
                    .background(SoftBlue)
            ) {
                ProductImagePreview(product = product)

                if (product.discountPercent > 0) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(DangerRed)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Text(
                            text = "${product.discountPercent}% OFF",
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = product.brand,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.title,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "⭐ ${product.rating} (${product.reviewCount})",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = product.category,
                style = MaterialTheme.typography.bodySmall,
                color = PrimaryBlue
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.price,
                    style = MaterialTheme.typography.titleMedium,
                    color = PrimaryBlue,
                    fontWeight = FontWeight.Bold
                )

                if (product.originalPrice.isNotBlank()) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = product.originalPrice,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductImagePreview(product: Product) {
    SubcomposeAsyncImage(
        model = product.imageUrl,
        contentDescription = product.title,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        loading = { ProductImageFallback(product) },
        error = { ProductImageFallback(product) }
    )
}

@Composable
private fun ProductImageFallback(product: Product) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when (product.category) {
                "Serum" -> "💧"
                "Sun Care" -> "☀️"
                "Cream" -> "🫙"
                "Cleanser" -> "🫧"
                "Lip Care" -> "💄"
                "Ampoule" -> "🩵"
                else -> "🧴"
            },
            style = MaterialTheme.typography.displaySmall
        )
    }
}