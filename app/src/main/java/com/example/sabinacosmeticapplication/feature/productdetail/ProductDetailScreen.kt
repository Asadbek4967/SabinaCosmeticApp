package com.example.sabinacosmeticapplication.feature.productdetail

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

private val DetailBackground = Color(0xFFF6F7FB)
private val DetailPrimary = Color(0xFF4D6BFE)
private val DetailMutedText = Color(0xFF7E8794)
private val DetailDiscountColor = Color(0xFFE53935)
private val DetailSurface = Color.White
private val DetailTitle = Color(0xFF1B1F26)
private val DetailBody = Color(0xFF4A5565)

@Composable
fun ProductDetailScreen(
    padding: PaddingValues,
    uiState: ProductDetailUiState,
    onBackClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    val product = uiState.product

    if (product == null) {
        ProductNotFoundState(
            padding = padding,
            onBackClick = onBackClick
        )
        return
    }

    val displayRating = remember(product.rating) {
        product.rating.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DetailBackground)
            .padding(padding)
            .navigationBarsPadding()
    ) {
        ProductDetailTopBar(
            title = "Product detail",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = DetailSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = product.imageUrl,
                        contentDescription = product.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop,
                        loading = { DetailImageFallback(product.category) },
                        error = { DetailImageFallback(product.category) }
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = product.brand,
                        style = MaterialTheme.typography.bodyMedium,
                        color = DetailMutedText
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = DetailTitle
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ProductMetaChip(text = product.category)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = product.price,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = DetailPrimary
                        )

                        if (product.originalPrice.isNotBlank()) {
                            Text(
                                text = product.originalPrice,
                                style = MaterialTheme.typography.bodyMedium,
                                color = DetailMutedText,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                    }

                    if (product.discountPercent > 0) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${product.discountPercent}% OFF",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = DetailDiscountColor
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "⭐ $displayRating (${product.reviewCount} reviews)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = DetailBody
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = DetailTitle
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = product.description.ifBlank { "No description yet." },
                        style = MaterialTheme.typography.bodyMedium,
                        color = DetailBody
                    )
                }
            }
        }

        Surface(
            color = DetailSurface,
            tonalElevation = 6.dp,
            shadowElevation = 6.dp
        ) {
            Button(
                onClick = onAddToCart,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DetailPrimary,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Add to cart",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun ProductDetailTopBar(
    title: String,
    onBackClick: () -> Unit
) {
    Surface(
        tonalElevation = 2.dp,
        color = DetailSurface
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = title,
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = DetailTitle
            )
        }
    }
}

@Composable
private fun ProductMetaChip(
    text: String
) {
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = Color(0xFFEAF3FF)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelLarge,
            color = DetailPrimary,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ProductNotFoundState(
    padding: PaddingValues,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DetailBackground)
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(72.dp),
                shape = CircleShape,
                color = Color(0xFFEAF3FF)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "🛍️",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Product not found",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = DetailTitle
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "This product is unavailable or could not be loaded.",
                style = MaterialTheme.typography.bodyMedium,
                color = DetailMutedText
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onBackClick,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Go back")
            }
        }
    }
}

@Composable
private fun DetailImageFallback(category: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF3FF)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when (category) {
                "Serum" -> "💧"
                "Sun Care" -> "☀️"
                "Cream" -> "🫙"
                "Cleanser" -> "🫧"
                "Lip Care" -> "💄"
                "Ampoule" -> "🩵"
                "Toner" -> "✨"
                else -> "🧴"
            },
            style = MaterialTheme.typography.displaySmall
        )
    }
}