package com.example.sabinacosmeticapplication.feature.productdetail

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sabinacosmeticapplication.ui.components.common.AppSectionTitle
import com.example.sabinacosmeticapplication.ui.components.common.AppTopBar
import com.example.sabinacosmeticapplication.ui.components.product.ProductBadge
import com.example.sabinacosmeticapplication.ui.components.product.ProductImage
import com.example.sabinacosmeticapplication.ui.components.product.ProductPriceBlock
import com.example.sabinacosmeticapplication.ui.components.product.ProductStatusBadge
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.isAddedToCart) {
        if (uiState.isAddedToCart) {
            snackbarHostState.showSnackbar(
                message = "Product added to cart",
                duration = SnackbarDuration.Short
            )
            viewModel.consumeAddedToCartState()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppColors.Background,
        topBar = {
            AppTopBar(
                title = "Product Detail",
                onBackClick = onBackClick,
                actions = {
                    IconButton(onClick = onCartClick) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = "Cart",
                            tint = AppColors.Primary
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            uiState.product?.let { product ->
                ProductDetailBottomBar(
                    price = product.price,
                    onAddToCartClick = viewModel::addToCart
                )
            }
        }
    ) { innerPadding ->
        when {
            uiState.isLoading -> {
                ProductDetailLoading(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            uiState.product != null -> {
                ProductDetailContent(
                    uiState = uiState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            else -> {
                ProductDetailError(
                    message = uiState.errorMessage ?: "Unknown error",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun ProductDetailLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(AppColors.Background),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = AppColors.Primary
        )
    }
}

@Composable
private fun ProductDetailError(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(AppColors.Background),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = AppShapes.ExtraLarge,
            color = AppColors.Surface,
            shadowElevation = AppDimens.CardElevation,
            modifier = Modifier.padding(horizontal = AppDimens.ScreenHorizontal)
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(AppDimens.Space20),
                style = MaterialTheme.typography.bodyLarge,
                color = AppColors.SecondaryText
            )
        }
    }
}

@Composable
private fun ProductDetailContent(
    uiState: ProductDetailUiState,
    modifier: Modifier = Modifier
) {
    val product = uiState.product ?: return

    Column(
        modifier = modifier
            .background(AppColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = AppDimens.ScreenHorizontal,
                vertical = AppDimens.Space16
            )
    ) {
        Card(
            shape = AppShapes.ExtraLarge,
            colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
            elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.CardElevation)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppDimens.Space20),
                contentAlignment = Alignment.Center
            ) {
                ProductImage(
                    imageUrl = product.imageUrl,
                    contentDescription = product.title,
                    size = AppDimens.ProductImageXL,
                    badgeText = product.category
                )
            }
        }

        Spacer(modifier = Modifier.height(AppDimens.Space20))

        ProductBadgeRow(
            category = product.category,
            isBestSeller = product.isBestSeller,
            isFlashSale = product.isFlashSale
        )

        if (product.brand.isNotBlank()) {
            Spacer(modifier = Modifier.height(AppDimens.Space12))
            Text(
                text = product.brand,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = AppColors.SecondaryText,
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.height(AppDimens.Space6))

        Text(
            text = product.title,
            style = MaterialTheme.typography.headlineSmall.copy(
                color = AppColors.Primary,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(AppDimens.Space12))

        ProductPriceBlock(
            price = product.price,
            oldPrice = product.oldPrice,
            discountLabel = product.discountLabel
        )

        Spacer(modifier = Modifier.height(AppDimens.Space24))

        AppSectionTitle(
            title = "Description",
            subtitle = "About this product"
        )

        Card(
            shape = AppShapes.Large,
            colors = CardDefaults.cardColors(containerColor = AppColors.Surface)
        ) {
            Text(
                text = product.description,
                modifier = Modifier.padding(AppDimens.Space16),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = AppColors.SecondaryText
                )
            )
        }

        Spacer(modifier = Modifier.height(AppDimens.Space20))

        AppSectionTitle(
            title = "Product Info",
            subtitle = "Basic product details"
        )

        Card(
            shape = AppShapes.Large,
            colors = CardDefaults.cardColors(containerColor = AppColors.Surface)
        ) {
            Column(
                modifier = Modifier.padding(AppDimens.Space16),
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
            ) {
                ProductInfoRow(label = "Brand", value = product.brand.ifBlank { "-" })
                ProductInfoRow(label = "Category", value = product.category.ifBlank { "-" })
                ProductInfoRow(label = "Product ID", value = product.id)
            }
        }

        Spacer(modifier = Modifier.height(AppDimens.Space32))
        Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
    }
}

@Composable
private fun ProductBadgeRow(
    category: String,
    isBestSeller: Boolean,
    isFlashSale: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (category.isNotBlank()) {
            ProductBadge(text = category)
        }

        ProductStatusBadge(
            isBestSeller = isBestSeller,
            isFlashSale = isFlashSale
        )
    }
}

@Composable
private fun ProductInfoRow(
    label: String,
    value: String
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = AppColors.SecondaryText
                )
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = AppColors.Primary,
                    fontWeight = FontWeight.SemiBold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.height(AppDimens.Space12))
        HorizontalDivider(color = AppColors.Divider)
    }
}

@Composable
private fun ProductDetailBottomBar(
    price: String,
    onAddToCartClick: () -> Unit
) {
    Surface(
        color = AppColors.Surface,
        shadowElevation = AppDimens.Space8
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    horizontal = AppDimens.ScreenHorizontal,
                    vertical = AppDimens.Space14
                ),
            horizontalArrangement = Arrangement.spacedBy(AppDimens.Space12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Price",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = AppColors.SecondaryText
                    )
                )

                Text(
                    text = price,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = AppColors.Price,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Button(
                onClick = onAddToCartClick,
                shape = AppShapes.Pill,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Primary,
                    contentColor = AppColors.OnPrimary
                ),
                contentPadding = PaddingValues(
                    horizontal = AppDimens.Space20,
                    vertical = AppDimens.Space12
                )
            ) {
                Text(
                    text = "Add to Cart",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}