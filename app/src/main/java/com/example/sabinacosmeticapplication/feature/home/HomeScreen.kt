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
import androidx.compose.foundation.layout.size
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalMall
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
import com.example.sabinacosmeticapplication.ui.components.category.CategoryVisualResolver
import com.example.sabinacosmeticapplication.ui.components.common.AppSectionTitle
import com.example.sabinacosmeticapplication.ui.components.product.VerticalProductCard
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

private val BannerBlueStart = Color(0xFF1677FF)
private val BannerBlueEnd = Color(0xFF65A9FF)
private val BannerPinkStart = Color(0xFFFF4D7D)
private val BannerPinkEnd = Color(0xFFFF9F7A)
private val BannerPurpleStart = Color(0xFF7C3AED)
private val BannerPurpleEnd = Color(0xFFA78BFA)
private val IndicatorInactive = Color(0xFFD7DCE5)
private val CoupangBlue = Color(0xFF1677FF)
private val SaleRed = Color(0xFFFF3B30)
private val CouponBlueSoft = Color(0xFFEAF2FF)
private val HomeTopSafeSpacing = 10.dp

@Composable
fun HomeScreen(
    padding: PaddingValues,
    uiState: HomeUiState,
    onAction: (HomeUiAction) -> Unit
) {
    when {
        uiState.isLoading -> HomeLoadingContent(padding)

        uiState.showError -> HomeErrorContent(
            padding = padding,
            message = uiState.errorMessage ?: "Something went wrong",
            onRetryClick = { onAction(HomeUiAction.RetryClick) }
        )

        uiState.showEmpty -> HomeEmptyContent(padding)

        else -> HomeContent(
            padding = padding,
            uiState = uiState,
            onAction = onAction
        )
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
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(
            top = HomeTopSafeSpacing,
            bottom = padding.calculateBottomPadding() + 24.dp
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
                CoupangCategoryShortcutGrid(
                    categories = uiState.categories,
                    onCategoryClick = { title ->
                        onAction(HomeUiAction.CategoryClick(title))
                    }
                )
            }
        }

        item {
            CouponPackCard()
        }

        if (uiState.featuredProducts.isNotEmpty()) {
            item {
                ProductCarouselSection(
                    title = "이 상품 놓치지 마세요!",
                    subtitle = "Sabina Cosmetic 추천 상품",
                    products = uiState.featuredProducts,
                    onProductClick = { onAction(HomeUiAction.ProductClick(it)) }
                )
            }
        }

        if (uiState.flashSaleProducts.isNotEmpty()) {
            item {
                ProductCarouselSection(
                    title = "Flash Sale",
                    subtitle = "오늘만 특별 할인",
                    products = uiState.flashSaleProducts,
                    onProductClick = { onAction(HomeUiAction.ProductClick(it)) },
                    titleColor = SaleRed
                )
            }
        }

        if (uiState.bestSellerProducts.isNotEmpty()) {
            item {
                ProductCarouselSection(
                    title = "Best Sellers",
                    subtitle = "고객들이 많이 찾는 상품",
                    products = uiState.bestSellerProducts,
                    onProductClick = { onAction(HomeUiAction.ProductClick(it)) }
                )
            }
        }

        if (uiState.recommendedProducts.isNotEmpty()) {
            item {
                ProductGridSection(
                    title = "Recommended for you",
                    subtitle = "당신을 위한 추천 상품",
                    products = uiState.recommendedProducts,
                    onProductClick = { onAction(HomeUiAction.ProductClick(it)) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
        }
    }
}

@Composable
private fun HomeTopSection(
    banners: List<PromoBannerUi>,
    onSearchClick: () -> Unit
) {
    val safeBanners = remember(banners) {
        banners.ifEmpty {
            listOf(
                PromoBannerUi(
                    title = "Sabina Beauty Week",
                    subtitle = "Korean skincare products up to 25% OFF",
                    colors = listOf(BannerBlueStart, BannerBlueEnd)
                ),
                PromoBannerUi(
                    title = "Glow Essentials",
                    subtitle = "Serum, toner, cream and sunscreen deals",
                    colors = listOf(BannerPinkStart, BannerPinkEnd)
                ),
                PromoBannerUi(
                    title = "Best Seller Picks",
                    subtitle = "Most loved cosmetic products",
                    colors = listOf(BannerPurpleStart, BannerPurpleEnd)
                )
            )
        }
    }

    val pagerState = rememberPagerState(pageCount = { safeBanners.size })

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HomeHeader()
            SearchBarFake(onClick = onSearchClick)
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            PromoBanner(banner = safeBanners[page])
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Sabina Cosmetic",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            color = AppColors.Primary
        )

        Surface(
            shape = CircleShape,
            color = AppColors.Surface,
            shadowElevation = 4.dp
        ) {
            Box(
                modifier = Modifier.padding(11.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.NotificationsNone,
                    contentDescription = "Notifications",
                    tint = AppColors.IconTint,
                    modifier = Modifier.size(22.dp)
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
        shape = RoundedCornerShape(999.dp),
        color = AppColors.Surface,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                tint = AppColors.Primary,
                modifier = Modifier.size(22.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "Sabina Cosmetic에서 검색하세요!",
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
    val bannerColors = banner.colors.ifEmpty {
        listOf(BannerBlueStart, BannerBlueEnd)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .background(Brush.horizontalGradient(bannerColors))
            .padding(horizontal = 24.dp, vertical = 22.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                shape = AppShapes.Pill,
                color = Color.White.copy(alpha = 0.22f)
            ) {
                Text(
                    text = "최대 25% OFF",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = banner.title,
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                maxLines = 2
            )

            Text(
                text = banner.subtitle,
                color = Color.White.copy(alpha = 0.92f),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )
        }

        Surface(
            modifier = Modifier.align(Alignment.BottomEnd),
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.18f)
        ) {
            Icon(
                imageVector = Icons.Outlined.LocalMall,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(18.dp)
                    .size(34.dp)
            )
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
                    .padding(horizontal = 3.dp)
                    .height(5.dp)
                    .width(if (currentPage == index) 18.dp else 5.dp)
                    .clip(AppShapes.Pill)
                    .background(if (currentPage == index) CoupangBlue else IndicatorInactive)
            )
        }
    }
}

@Composable
private fun CoupangCategoryShortcutGrid(
    categories: List<CategoryUi>,
    onCategoryClick: (String) -> Unit
) {
    val visibleCategories = remember(categories) {
        categories.take(10)
    }

    val firstRow = visibleCategories.take(5)
    val secondRow = visibleCategories.drop(5).take(5)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Surface)
            .padding(vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        CategoryShortcutRow(
            categories = firstRow,
            onCategoryClick = onCategoryClick
        )

        if (secondRow.isNotEmpty()) {
            CategoryShortcutRow(
                categories = secondRow,
                onCategoryClick = onCategoryClick
            )
        }
    }
}

@Composable
private fun CategoryShortcutRow(
    categories: List<CategoryUi>,
    onCategoryClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        categories.forEach { category ->
            CoupangCategoryShortcutItem(
                category = category,
                onClick = { onCategoryClick(category.title) }
            )
        }

        repeat(5 - categories.size) {
            Spacer(modifier = Modifier.width(58.dp))
        }
    }
}

@Composable
private fun CoupangCategoryShortcutItem(
    category: CategoryUi,
    onClick: () -> Unit
) {
    val visual = remember(category.iconName, category.title) {
        CategoryVisualResolver.resolve(
            iconName = category.iconName,
            title = category.title
        )
    }

    Column(
        modifier = Modifier
            .width(64.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Surface(
            modifier = Modifier.size(52.dp),
            shape = RoundedCornerShape(17.dp),
            color = Color.Transparent,
            shadowElevation = 4.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(visual.backgroundBrush),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = visual.icon,
                    contentDescription = category.title,
                    tint = visual.contentColor,
                    modifier = Modifier.size(29.dp)
                )
            }
        }

        Text(
            text = CategoryMapper.toDisplayName(category.title),
            color = AppColors.Primary,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun CouponPackCard() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        shape = RoundedCornerShape(18.dp),
        color = CouponBlueSoft
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "[미사용] 15,000원 쿠폰팩이 있습니다.",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AppColors.Primary
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = CoupangBlue
            ) {
                Text(
                    text = "확인",
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ProductCarouselSection(
    title: String,
    subtitle: String,
    products: List<Product>,
    onProductClick: (String) -> Unit,
    titleColor: Color = AppColors.Primary
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AppSectionTitle(
            title = title,
            subtitle = subtitle,
            titleColor = titleColor,
            subtitleColor = AppColors.SecondaryText,
            modifier = Modifier.padding(horizontal = 18.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
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
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AppSectionTitle(
            title = title,
            subtitle = subtitle,
            titleColor = AppColors.Primary,
            subtitleColor = AppColors.SecondaryText,
            modifier = Modifier.padding(horizontal = 18.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(AppDimens.HomeRecommendedGridHeight)
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CircularProgressIndicator(color = AppColors.Primary)

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
                start = 18.dp,
                end = 18.dp,
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
                start = 18.dp,
                end = 18.dp,
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
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
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
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = AppColors.OnPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}