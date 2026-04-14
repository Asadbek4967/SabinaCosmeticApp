package com.example.sabinacosmeticapplication.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.data.mapper.CategoryMapper
import com.example.sabinacosmeticapplication.data.mapper.toUiProduct
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.ui.components.common.AppSectionTitle
import com.example.sabinacosmeticapplication.ui.components.product.VerticalProductCard
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

private val BannerFallbackStart = Color(0xFF2563EB)
private val BannerFallbackEnd = Color(0xFF60A5FA)
private val IndicatorInactive = Color(0xFFD7DCE5)
private val HomeTopSafeSpacing = 12.dp

@Composable
fun HomeScreen(
    padding: PaddingValues,
    uiState: HomeUiState,
    onAction: (HomeUiAction) -> Unit
) {
    when {
        uiState.isLoading -> {
            HomeLoadingContent(padding = padding)
        }

        uiState.showError -> {
            HomeErrorContent(
                padding = padding,
                message = uiState.errorMessage ?: "Something went wrong",
                onRetryClick = { onAction(HomeUiAction.RetryClick) }
            )
        }

        uiState.showEmpty -> {
            HomeEmptyContent(padding = padding)
        }

        else -> {
            HomeContent(
                padding = padding,
                uiState = uiState,
                onAction = onAction
            )
        }
    }
}

@Composable
private fun HomeContent(
    padding: PaddingValues,
    uiState: HomeUiState,
    onAction: (HomeUiAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(AppDimens.HomeSectionSpacing),
        contentPadding = PaddingValues(
            top = HomeTopSafeSpacing,
            bottom = padding.calculateBottomPadding() + AppDimens.Space24
        )
    ) {
        item {
            HomeTopSection(
                banners = uiState.banners,
                onSearchClick = { onAction(HomeUiAction.SearchClick) }
            )
        }

        if (uiState.categories.isNotEmpty()) {
            item {
                CategorySection(
                    categories = uiState.categories,
                    onCategoryClick = { category ->
                        onAction(HomeUiAction.CategoryClick(category))
                    },
                    onSeeAllClick = {
                        onAction(HomeUiAction.CategoriesSeeAllClick)
                    }
                )
            }
        }

        if (uiState.featuredProducts.isNotEmpty()) {
            item {
                ProductCarouselSection(
                    title = "Featured picks",
                    subtitle = "Curated beauty essentials worth exploring",
                    products = uiState.featuredProducts,
                    onProductClick = { productId ->
                        onAction(HomeUiAction.ProductClick(productId))
                    }
                )
            }
        }

        if (uiState.flashSaleProducts.isNotEmpty()) {
            item {
                ProductCarouselSection(
                    title = "Flash Sale",
                    subtitle = "Limited-time offers selected for you",
                    products = uiState.flashSaleProducts,
                    onProductClick = { productId ->
                        onAction(HomeUiAction.ProductClick(productId))
                    }
                )
            }
        }

        if (uiState.bestSellerProducts.isNotEmpty()) {
            item {
                ProductCarouselSection(
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
                ProductGridSection(
                    title = "Recommended for you",
                    subtitle = "Picked for your beauty routine",
                    products = uiState.recommendedProducts,
                    onProductClick = { productId ->
                        onAction(HomeUiAction.ProductClick(productId))
                    }
                )
            }
        }

        item {
            Spacer(
                modifier = Modifier.windowInsetsBottomHeight(
                    WindowInsets.safeDrawing
                )
            )
        }
    }
}

@Composable
private fun HomeLoadingContent(
    padding: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .statusBarsPadding()
            .padding(bottom = padding.calculateBottomPadding()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
        ) {
            CircularProgressIndicator(
                color = AppColors.Primary
            )

            Text(
                text = "Loading home...",
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.SecondaryText
            )
        }
    }
}

@Composable
private fun HomeErrorContent(
    padding: PaddingValues,
    message: String,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .statusBarsPadding()
            .padding(
                start = AppDimens.ScreenHorizontal,
                end = AppDimens.ScreenHorizontal,
                top = HomeTopSafeSpacing,
                bottom = padding.calculateBottomPadding()
            ),
        contentAlignment = Alignment.Center
    ) {
        StateCard(
            title = "Unable to load home",
            message = message.ifBlank { "Something went wrong. Please try again." },
            actionLabel = "Try again",
            onActionClick = onRetryClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun HomeEmptyContent(
    padding: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .statusBarsPadding()
            .padding(
                start = AppDimens.ScreenHorizontal,
                end = AppDimens.ScreenHorizontal,
                top = HomeTopSafeSpacing,
                bottom = padding.calculateBottomPadding()
            ),
        contentAlignment = Alignment.Center
    ) {
        StateCard(
            title = "No products available",
            message = "Please check back later for new arrivals and offers.",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun StateCard(
    title: String,
    message: String,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = AppShapes.ExtraLarge,
        color = AppColors.Surface,
        shadowElevation = AppDimens.CardElevation
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = AppDimens.Space20,
                vertical = AppDimens.Space24
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = AppColors.Primary,
                textAlign = TextAlign.Center
            )

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.SecondaryText,
                textAlign = TextAlign.Center
            )

            if (!actionLabel.isNullOrBlank() && onActionClick != null) {
                Surface(
                    modifier = Modifier.clickable(onClick = onActionClick),
                    shape = AppShapes.Pill,
                    color = AppColors.Primary
                ) {
                    Text(
                        text = actionLabel,
                        modifier = Modifier.padding(
                            horizontal = AppDimens.Space16,
                            vertical = AppDimens.Space10
                        ),
                        style = MaterialTheme.typography.labelLarge,
                        color = AppColors.OnPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeTopSection(
    banners: List<PromoBannerUi>,
    onSearchClick: () -> Unit
) {
    val safeBanners = remember(banners) {
        if (banners.isNotEmpty()) {
            banners
        } else {
            listOf(
                PromoBannerUi(
                    title = "Sabina Picks",
                    subtitle = "Discover curated Korean beauty essentials for everyday care.",
                    colors = listOf(BannerFallbackStart, BannerFallbackEnd)
                )
            )
        }
    }

    val pagerState = rememberPagerState(pageCount = { safeBanners.size })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = AppDimens.ScreenHorizontal,
                vertical = AppDimens.Space14
            ),
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space16)
    ) {
        HomeHeader()
        SearchBarFake(onClick = onSearchClick)

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            PromoBanner(
                banner = safeBanners[page]
            )
        }

        BannerIndicatorRow(
            pageCount = safeBanners.size,
            currentPage = pagerState.currentPage
        )
    }
}

@Composable
private fun HomeHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space4)
        ) {
            Text(
                text = "Sabina Cosmetic",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = AppColors.Primary
            )

            Text(
                text = "Korean beauty marketplace",
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.SecondaryText
            )
        }

        Surface(
            shape = CircleShape,
            color = AppColors.Surface,
            shadowElevation = AppDimens.CardElevation
        ) {
            Box(
                modifier = Modifier.padding(AppDimens.Space10),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.NotificationsNone,
                    contentDescription = "Notifications",
                    tint = AppColors.IconTint
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
            .clickable(onClick = onClick),
        shape = AppShapes.Large,
        color = AppColors.Surface,
        shadowElevation = AppDimens.CardElevation
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
                contentDescription = "Search",
                tint = AppColors.SecondaryText
            )

            Spacer(modifier = Modifier.width(AppDimens.Space10))

            Text(
                text = "Search skincare, serum, cream...",
                color = AppColors.SecondaryText,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun PromoBanner(
    banner: PromoBannerUi
) {
    val bannerColors = if (banner.colors.isNotEmpty()) {
        banner.colors
    } else {
        listOf(BannerFallbackStart, BannerFallbackEnd)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(AppShapes.ExtraLarge)
            .background(
                brush = Brush.horizontalGradient(
                    colors = bannerColors
                )
            )
            .padding(AppDimens.Space20)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space8)
        ) {
            Text(
                text = banner.title,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = banner.subtitle,
                color = Color.White.copy(alpha = 0.92f),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(AppDimens.Space4))

            Box(
                modifier = Modifier
                    .clip(AppShapes.Pill)
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
private fun BannerIndicatorRow(
    pageCount: Int,
    currentPage: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Space4)
                    .height(AppDimens.HomeBannerIndicatorHeight)
                    .width(
                        if (currentPage == index) {
                            AppDimens.HomeBannerIndicatorActiveWidth
                        } else {
                            AppDimens.HomeBannerIndicatorInactiveWidth
                        }
                    )
                    .clip(AppShapes.Pill)
                    .background(
                        if (currentPage == index) AppColors.Accent else IndicatorInactive
                    )
            )
        }
    }
}

@Composable
private fun CategorySection(
    categories: List<CategoryUi>,
    onCategoryClick: (String) -> Unit,
    onSeeAllClick: () -> Unit
) {
    val rows = remember(categories) { categories.chunked(3) }

    Column(
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
    ) {
        SectionHeader(
            title = "Categories",
            subtitle = "Browse by type",
            actionLabel = "See all",
            onActionClick = onSeeAllClick
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppDimens.ScreenHorizontal),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
        ) {
            rows.forEach { rowCategories ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.Space12)
                ) {
                    rowCategories.forEach { category ->
                        CategoryGridItem(
                            category = category,
                            onClick = { onCategoryClick(category.title) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    repeat(3 - rowCategories.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    subtitle: String,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.ScreenHorizontal),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space4)
        ) {
            Text(
                text = title,
                color = AppColors.Primary,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = subtitle,
                color = AppColors.SecondaryText,
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (!actionLabel.isNullOrBlank() && onActionClick != null) {
            Text(
                text = actionLabel,
                color = AppColors.Accent,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable(onClick = onActionClick)
            )
        }
    }
}

@Composable
private fun CategoryGridItem(
    category: CategoryUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clickable(onClick = onClick),
        shape = AppShapes.Large,
        color = AppColors.Surface,
        tonalElevation = 0.dp,
        shadowElevation = AppDimens.CardElevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppDimens.Space8,
                    vertical = AppDimens.Space14
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space8)
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(AppColors.SurfaceVariant)
                    .padding(AppDimens.Space12),
                contentAlignment = Alignment.Center
            ) {
                Text(text = category.iconEmoji)
            }

            Text(
                text = CategoryMapper.toDisplayName(category.title),
                color = AppColors.Primary,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                minLines = 2,
                maxLines = 2
            )
        }
    }
}

@Composable
private fun ProductCarouselSection(
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
            titleColor = AppColors.Primary,
            subtitleColor = AppColors.SecondaryText,
            modifier = Modifier.padding(horizontal = AppDimens.ScreenHorizontal)
        )

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
                        product = product.toUiProduct(),
                        onClick = { onProductClick(product.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductGridSection(
    title: String,
    subtitle: String,
    products: List<Product>,
    onProductClick: (String) -> Unit
) {
    val safeProducts = remember(products) {
        products.take(AppDimens.HomeRecommendedMaxItems)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
    ) {
        AppSectionTitle(
            title = title,
            subtitle = subtitle,
            titleColor = AppColors.Primary,
            subtitleColor = AppColors.SecondaryText,
            modifier = Modifier.padding(horizontal = AppDimens.ScreenHorizontal)
        )

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
                    product = product.toUiProduct(),
                    onClick = { onProductClick(product.id) }
                )
            }
        }
    }
}