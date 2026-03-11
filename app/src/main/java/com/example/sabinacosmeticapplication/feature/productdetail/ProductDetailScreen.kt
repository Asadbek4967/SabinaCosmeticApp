package com.example.sabinacosmeticapplication.feature.productdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun ProductDetailScreen(
    padding: PaddingValues,
    uiState: ProductDetailUiState,
    onBackClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    val product = uiState.product

    if (product == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Mahsulot topilmadi")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F7FB))
            .padding(padding)
            .navigationBarsPadding()
    ) {
        Surface(
            tonalElevation = 2.dp,
            color = Color.White
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
                    text = "Product detail",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = product.imageUrl,
                        contentDescription = product.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .clip(RoundedCornerShape(18.dp)),
                        contentScale = ContentScale.Crop,
                        loading = {
                            DetailImageFallback(product.category)
                        },
                        error = {
                            DetailImageFallback(product.category)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = product.brand,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = product.category,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF4D6BFE)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = product.price,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF4D6BFE),
                        fontWeight = FontWeight.Bold
                    )

                    if (product.originalPrice.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = product.originalPrice,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }

                    if (product.discountPercent > 0) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${product.discountPercent}% OFF",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "⭐ ${product.rating}  (${product.reviewCount})",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = product.description.ifBlank { "No description yet." },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Surface(
            color = Color.White,
            tonalElevation = 4.dp
        ) {
            Button(
                onClick = onAddToCart,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Add to cart")
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
                else -> "🧴"
            },
            style = MaterialTheme.typography.displaySmall
        )
    }
}