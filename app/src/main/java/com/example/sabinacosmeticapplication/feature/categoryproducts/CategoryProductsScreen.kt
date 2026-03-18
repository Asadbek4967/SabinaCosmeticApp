package com.example.sabinacosmeticapplication.feature.categoryproducts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inventory2
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
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.ui.components.AppTopBar
import com.example.sabinacosmeticapplication.ui.components.VerticalProductCard
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun CategoryProductsScreen(
    padding: PaddingValues,
    uiState: CategoryProductsUiState,
    onBackClick: () -> Unit,
    onProductClick: (String) -> Unit
) {
    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            AppTopBar(
                title = uiState.categoryName.ifBlank { "Category" },
                subtitle = if (uiState.isLoading) null else "${uiState.products.size} products",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        when {
            uiState.isLoading -> {
                LoadingContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppColors.Background)
                        .padding(innerPadding)
                        .padding(padding)
                )
            }

            uiState.products.isEmpty() -> {
                EmptyCategoryContent(
                    categoryName = uiState.categoryName,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppColors.Background)
                        .padding(innerPadding)
                        .padding(padding)
                )
            }

            else -> {
                CategoryProductsGrid(
                    padding = padding,
                    innerPadding = innerPadding,
                    categoryName = uiState.categoryName,
                    products = uiState.products,
                    onProductClick = onProductClick
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
    products: List<Product>,
    onProductClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(innerPadding)
            .padding(padding),
        contentPadding = PaddingValues(
            start = AppDimens.ScreenHorizontal,
            end = AppDimens.ScreenHorizontal,
            top = AppDimens.Space16,
            bottom = AppDimens.Space24
        ),
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space14),
        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space14)
    ) {
        item(span = { GridItemSpan(2) }) {
            CategoryIntroCard(categoryName = categoryName)
        }

        items(
            items = products,
            key = { it.id }
        ) { product ->
            VerticalProductCard(
                product = product,
                onClick = { onProductClick(product.id) }
            )
        }
    }
}

@Composable
private fun CategoryIntroCard(
    categoryName: String
) {
    Surface(
        shape = AppShapes.ExtraLarge,
        color = AppColors.Surface,
        shadowElevation = AppDimens.CardElevation
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.Space16)
        ) {
            Text(
                text = categoryName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AppColors.Primary
            )

            Text(
                text = "Explore curated $categoryName essentials for your beauty routine.",
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.SecondaryText,
                modifier = Modifier.padding(top = AppDimens.Space6)
            )
        }
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = AppColors.Primary)
    }
}

@Composable
private fun EmptyCategoryContent(
    categoryName: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = AppShapes.ExtraLarge,
            color = AppColors.Surface,
            shadowElevation = AppDimens.CardElevation,
            modifier = Modifier.padding(horizontal = AppDimens.ScreenHorizontal)
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = AppDimens.Space20,
                    vertical = AppDimens.Space24
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.Inventory2,
                    contentDescription = null,
                    tint = AppColors.SecondaryText
                )

                Text(
                    text = "No products found",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.Primary,
                    modifier = Modifier.padding(top = AppDimens.Space12)
                )

                Text(
                    text = "There are currently no items in $categoryName.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.SecondaryText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = AppDimens.Space6)
                )
            }
        }
    }
}