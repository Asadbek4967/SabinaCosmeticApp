package com.example.sabinacosmeticapplication.feature.favorites

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.DeleteOutline
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.sabinacosmeticapplication.data.mapper.toUiProduct
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.ui.components.common.AppTopBar
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onProductClick: (String) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            viewModel.consumeUserMessage()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppColors.Background,
        topBar = {
            AppTopBar(
                title = "Wishlist",
                subtitle = if (uiState.products.isNotEmpty()) {
                    "${uiState.products.size} saved item${if (uiState.products.size > 1) "s" else ""}"
                } else {
                    "Products you want to keep an eye on"
                },
                onBackClick = onBackClick
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        when {
            uiState.isLoading -> {
                FavoritesLoading(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            uiState.errorMessage != null -> {
                FavoritesError(
                    message = uiState.errorMessage ?: "Unknown error",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            uiState.products.isEmpty() -> {
                FavoritesEmpty(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            else -> {
                FavoritesContent(
                    products = uiState.products,
                    onProductClick = onProductClick,
                    onRemoveClick = viewModel::removeFavorite,
                    onAddToCartClick = viewModel::addToCart,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun FavoritesLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(AppColors.Background),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = AppShapes.ExtraLarge,
            colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
            elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.CardElevation)
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = AppDimens.Space24,
                    vertical = AppDimens.Space24
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = AppColors.Primary)

                Spacer(modifier = Modifier.size(AppDimens.Space12))

                Text(
                    text = "Loading wishlist...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.SecondaryText
                )
            }
        }
    }
}

@Composable
private fun FavoritesError(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(AppColors.Background)
            .padding(horizontal = AppDimens.ScreenHorizontal),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = AppShapes.ExtraLarge,
            colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
            elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.CardElevation)
        ) {
            Column(
                modifier = Modifier.padding(AppDimens.Space24),
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space10),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Unable to load wishlist",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Primary
                )

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.SecondaryText
                )
            }
        }
    }
}

@Composable
private fun FavoritesEmpty(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(AppColors.Background)
            .padding(horizontal = AppDimens.ScreenHorizontal),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = AppShapes.ExtraLarge,
            colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
            elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.CardElevation)
        ) {
            Column(
                modifier = Modifier.padding(AppDimens.Space24),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.error.copy(alpha = 0.10f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }

                Text(
                    text = "No favorites yet",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Primary
                )

                Text(
                    text = "Save products you love and they will appear here.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.SecondaryText
                )
            }
        }
    }
}

@Composable
private fun FavoritesContent(
    products: List<Product>,
    onProductClick: (String) -> Unit,
    onRemoveClick: (String) -> Unit,
    onAddToCartClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.background(AppColors.Background),
        contentPadding = PaddingValues(
            horizontal = AppDimens.ScreenHorizontal,
            vertical = AppDimens.Space16
        ),
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
    ) {
        item {
            FavoritesInfoBanner(
                itemCount = products.size
            )
        }

        items(
            items = products,
            key = { it.id }
        ) { product ->
            FavoriteProductCard(
                product = product,
                onClick = { onProductClick(product.id) },
                onRemoveClick = { onRemoveClick(product.id) },
                onAddToCartClick = { onAddToCartClick(product) }
            )
        }

        item {
            Spacer(modifier = Modifier.navigationBarsPadding())
            Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
        }
    }
}

@Composable
private fun FavoritesInfoBanner(
    itemCount: Int
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppDimens.Space16,
                    vertical = AppDimens.Space14
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppDimens.Space12)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.error.copy(alpha = 0.10f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }

            Column {
                Text(
                    text = "Wishlist saved",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.Primary
                )

                Text(
                    text = "$itemCount saved item${if (itemCount > 1) "s" else ""} ready to review",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.SecondaryText
                )
            }
        }
    }
}

@Composable
private fun FavoriteProductCard(
    product: Product,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onAddToCartClick: () -> Unit
) {
    val displayProduct = product.toUiProduct()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = AppShapes.ExtraLarge,
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.CardElevation)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppDimens.Space16),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FavoriteProductImage(
                    imageUrl = displayProduct.imageUrl,
                    contentDescription = displayProduct.safeTitle
                )

                Spacer(modifier = Modifier.width(AppDimens.Space12))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (displayProduct.category.isNotBlank()) {
                        Text(
                            text = displayProduct.category,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = AppColors.Primary,
                                fontWeight = FontWeight.SemiBold
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    if (displayProduct.safeBrand.isNotBlank()) {
                        Text(
                            text = displayProduct.safeBrand,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = AppColors.SecondaryText,
                                fontWeight = FontWeight.Medium
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Text(
                        text = displayProduct.safeTitle,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = AppColors.Primary,
                            fontWeight = FontWeight.SemiBold
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = displayProduct.formattedPrice,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = AppColors.Price,
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(AppDimens.Space8))

                IconButton(
                    onClick = onRemoveClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.DeleteOutline,
                        contentDescription = "Remove favorite",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            HorizontalDivider(color = AppColors.Divider)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = AppDimens.Space16,
                        vertical = AppDimens.Space12
                    ),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onAddToCartClick,
                    shape = AppShapes.Pill,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Primary,
                        contentColor = AppColors.OnPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(AppDimens.Space8))

                    Text(
                        text = "Add to Cart",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoriteProductImage(
    imageUrl: String?,
    contentDescription: String
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.Background),
        modifier = Modifier.size(96.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}