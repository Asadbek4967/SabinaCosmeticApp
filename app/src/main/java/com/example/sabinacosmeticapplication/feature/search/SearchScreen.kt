package com.example.sabinacosmeticapplication.feature.search

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.example.sabinacosmeticapplication.data.model.Product

private val SearchScreenBackground = Color(0xFFF7F8FC)
private val SearchCardBackground = Color(0xFFFFFFFF)
private val SearchAccent = Color(0xFF4D6BFE)
private val SearchPrimaryText = Color(0xFF1F2430)
private val SearchSecondaryText = Color(0xFF7B8190)
private val SearchBorder = Color(0xFFE4E9F2)
private val SearchImageBackground = Color(0xFFF1F4FB)

private val BestSellerBackground = Color(0xFFFFF1E8)
private val BestSellerText = Color(0xFFFF7A00)

private val FlashSaleBackground = Color(0xFFFFEEF1)
private val FlashSaleText = Color(0xFFFF4D6D)

@Composable
fun SearchScreen(
    padding: PaddingValues,
    onProductClick: (String) -> Unit = {},
    viewModel: SearchViewModel = viewModel()
) {
    val uiState = viewModel.uiState
    val resultText = if (uiState.results.size == 1) {
        "1 result found"
    } else {
        "${uiState.results.size} results found"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SearchScreenBackground)
            .padding(padding)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Text(
            text = "Search",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = SearchPrimaryText
        )

        Text(
            text = "Find your favorite Korean skincare products",
            style = MaterialTheme.typography.bodyMedium,
            color = SearchSecondaryText,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.query,
            onValueChange = viewModel::onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Search products, brand, category",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SearchSecondaryText
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = SearchPrimaryText
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = SearchSecondaryText
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedBorderColor = SearchAccent,
                unfocusedBorderColor = SearchBorder,
                cursorColor = SearchAccent
            )
        )

        Text(
            text = resultText,
            modifier = Modifier.padding(top = 16.dp, bottom = 14.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = SearchSecondaryText,
            fontWeight = FontWeight.Medium
        )

        if (uiState.results.isEmpty()) {
            SearchEmptyState(query = uiState.query)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(
                    items = uiState.results,
                    key = { it.id }
                ) { product ->
                    SearchProductItem(
                        product = product,
                        onClick = { onProductClick(product.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchProductItem(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SearchCardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(SearchImageBackground),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    loading = { SearchImageFallback(product) },
                    error = { SearchImageFallback(product) }
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (product.isBestSeller) {
                        ProductBadge(
                            text = "Best Seller",
                            containerColor = BestSellerBackground,
                            textColor = BestSellerText
                        )
                    }

                    if (product.isFlashSale) {
                        ProductBadge(
                            text = "Flash Sale",
                            containerColor = FlashSaleBackground,
                            textColor = FlashSaleText
                        )
                    }
                }

                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = SearchPrimaryText,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = product.brand,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SearchSecondaryText,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = product.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = SearchAccent,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = product.price,
                    style = MaterialTheme.typography.titleMedium,
                    color = SearchAccent,
                    fontWeight = FontWeight.ExtraBold
                )

                if (product.originalPrice.isNotBlank() && product.originalPriceValue > product.priceValue) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = product.originalPrice,
                            style = MaterialTheme.typography.bodySmall,
                            color = SearchSecondaryText,
                            textDecoration = TextDecoration.LineThrough
                        )

                        if (product.discountPercent > 0) {
                            Text(
                                text = "${product.discountPercent}% OFF",
                                style = MaterialTheme.typography.bodySmall,
                                color = FlashSaleText,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                if (product.rating > 0.0 || product.reviewCount > 0) {
                    Text(
                        text = "⭐ ${product.rating} (${product.reviewCount})",
                        style = MaterialTheme.typography.bodySmall,
                        color = SearchSecondaryText
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductBadge(
    text: String,
    containerColor: Color,
    textColor: Color
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = containerColor
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun SearchImageFallback(product: Product) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SearchImageBackground),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = categoryEmoji(product.category),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun SearchEmptyState(query: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🔎",
                style = MaterialTheme.typography.displaySmall
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = if (query.isBlank()) {
                    "Search for your favorite skincare"
                } else {
                    "No results for \"$query\""
                },
                style = MaterialTheme.typography.titleMedium,
                color = SearchPrimaryText,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = if (query.isBlank()) {
                    "Try searching serum, toner, cream, lip care..."
                } else {
                    "Try another product name, brand, or category"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = SearchSecondaryText
            )
        }
    }
}

private fun categoryEmoji(category: String): String {
    return when (category.trim().lowercase()) {
        "serum" -> "💧"
        "sun care" -> "☀️"
        "cream" -> "🫙"
        "cleanser" -> "🫧"
        "lip care" -> "💄"
        "ampoule" -> "🩵"
        "toner" -> "✨"
        else -> "🧴"
    }
}