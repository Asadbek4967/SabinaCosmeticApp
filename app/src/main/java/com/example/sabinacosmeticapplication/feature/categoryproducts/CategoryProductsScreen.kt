package com.example.sabinacosmeticapplication.feature.categoryproducts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.sabinacosmeticapplication.data.mapper.toUiProduct
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.ui.components.common.AppTopBar
import com.example.sabinacosmeticapplication.ui.components.product.VerticalProductCard
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun CategoryProductsScreen(
    padding: PaddingValues,
    uiState: CategoryProductsUiState,
    onBackClick: () -> Unit,
    onProductClick: (String) -> Unit,
    onRetryClick: () -> Unit,
) {
    val displayCategoryName = uiState.categoryDisplayName.ifBlank { "Category" }
    val displaySubtitle = uiState.categorySubtitle.ifBlank {
        "Explore curated products for your beauty routine."
    }

    val visibleProducts = if (uiState.products.isNotEmpty()) {
        uiState.products
    } else {
        uiState.fallbackProducts
    }

    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            AppTopBar(
                title = displayCategoryName,
                subtitle = if (uiState.isLoading) null else "${visibleProducts.size} products",
                onBackClick = onBackClick,
            )
        },
    ) { innerPadding ->
        when {
            uiState.isLoading -> {
                LoadingContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppColors.Background)
                        .padding(innerPadding)
                        .padding(padding)
                        .navigationBarsPadding(),
                )
            }

            uiState.errorMessage != null -> {
                ErrorCategoryContent(
                    message = uiState.errorMessage,
                    onRetryClick = onRetryClick,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppColors.Background)
                        .padding(innerPadding)
                        .padding(padding)
                        .navigationBarsPadding(),
                )
            }

            visibleProducts.isEmpty() -> {
                EmptyCategoryContent(
                    categoryName = displayCategoryName,
                    subtitle = displaySubtitle,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppColors.Background)
                        .padding(innerPadding)
                        .padding(padding)
                        .navigationBarsPadding(),
                )
            }

            else -> {
                CategoryProductsGrid(
                    padding = padding,
                    innerPadding = innerPadding,
                    categoryName = displayCategoryName,
                    categorySubtitle = displaySubtitle,
                    products = visibleProducts,
                    onProductClick = onProductClick,
                    isFallbackMode = uiState.isFallbackMode,
                )
            }
        }
    }
}

@Composable
private fun CategoryProductsGrid(
    padding: PaddingValues,
    innerPadding: PaddingValues,
    categoryName: String,
    categorySubtitle: String,
    products: List<Product>,
    onProductClick: (String) -> Unit,
    isFallbackMode: Boolean,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(innerPadding)
            .padding(padding)
            .navigationBarsPadding(),
        contentPadding = PaddingValues(
            start = AppDimens.ScreenHorizontal,
            end = AppDimens.ScreenHorizontal,
            top = AppDimens.Space16,
            bottom = AppDimens.Space24,
        ),
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space14),
        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space14),
    ) {
        item(span = { GridItemSpan(2) }) {
            CategoryIntroCard(
                categoryName = categoryName,
                subtitle = categorySubtitle,
                isFallbackMode = isFallbackMode,
            )
        }

        items(
            items = products,
            key = { it.id },
        ) { product ->
            VerticalProductCard(
                product = product.toUiProduct(),
                onClick = { onProductClick(product.id) },
            )
        }
    }
}

@Composable
private fun CategoryIntroCard(
    categoryName: String,
    subtitle: String,
    isFallbackMode: Boolean,
) {
    Surface(
        shape = AppShapes.ExtraLarge,
        color = AppColors.Surface,
        shadowElevation = AppDimens.CardElevation,
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.Space16),
        ) {
            Text(
                text = categoryName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AppColors.Primary,
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.SecondaryText,
                modifier = Modifier.padding(top = AppDimens.Space6),
            )

            if (isFallbackMode) {
                Text(
                    text = "Showing recommended cosmetics for this category.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.Primary,
                    modifier = Modifier.padding(top = AppDimens.Space8),
                )
            }
        }
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = AppColors.Primary)
    }
}

@Composable
private fun ErrorCategoryContent(
    message: String,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            shape = AppShapes.ExtraLarge,
            color = AppColors.Surface,
            shadowElevation = AppDimens.CardElevation,
            modifier = Modifier.padding(horizontal = AppDimens.ScreenHorizontal),
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = AppDimens.Space20,
                    vertical = AppDimens.Space24,
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Outlined.ErrorOutline,
                    contentDescription = null,
                    tint = AppColors.SecondaryText,
                )

                Text(
                    text = "Something went wrong",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.Primary,
                    modifier = Modifier.padding(top = AppDimens.Space12),
                )

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.SecondaryText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = AppDimens.Space6),
                )

                Button(
                    onClick = onRetryClick,
                    modifier = Modifier.padding(top = AppDimens.Space16),
                ) {
                    Text(text = "Retry")
                }
            }
        }
    }
}

@Composable
private fun EmptyCategoryContent(
    categoryName: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            shape = AppShapes.ExtraLarge,
            color = AppColors.Surface,
            shadowElevation = AppDimens.CardElevation,
            modifier = Modifier.padding(horizontal = AppDimens.ScreenHorizontal),
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = AppDimens.Space20,
                    vertical = AppDimens.Space24,
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Inventory2,
                    contentDescription = null,
                    tint = AppColors.SecondaryText,
                )

                Text(
                    text = "No products found",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.Primary,
                    modifier = Modifier.padding(top = AppDimens.Space12),
                )

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.SecondaryText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = AppDimens.Space6),
                )

                Text(
                    text = "There are currently no matching items in $categoryName.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.SecondaryText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = AppDimens.Space6),
                )
            }
        }
    }
}