package com.example.sabinacosmeticapplication.feature.cart

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import coil.compose.SubcomposeAsyncImage

@Composable
fun CartScreen(
    padding: PaddingValues,
    viewModel: CartViewModel = viewModel()
) {
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val total = cartItems.sumOf { it.product.priceValue * it.quantity }

    if (cartItems.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .navigationBarsPadding()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Your cart is empty",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Add products from Home or Search.",
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .navigationBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cartItems) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(92.dp)
                                .background(Color(0xFFEFF3FF), RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            SubcomposeAsyncImage(
                                model = item.product.imageUrl,
                                contentDescription = item.product.title,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                loading = { CartImageFallback(item.product.category) },
                                error = { CartImageFallback(item.product.category) }
                            )
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 12.dp)
                        ) {
                            Text(
                                text = item.product.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(text = item.product.brand)
                            Text(
                                text = item.product.price,
                                color = Color(0xFF4D6BFE)
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextButton(onClick = {
                                        viewModel.decreaseQuantity(item.product.id)
                                    }) {
                                        Text("-")
                                    }

                                    Text(
                                        text = item.quantity.toString(),
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    )

                                    TextButton(onClick = {
                                        viewModel.increaseQuantity(item.product.id)
                                    }) {
                                        Text("+")
                                    }
                                }

                                IconButton(onClick = {
                                    viewModel.removeFromCart(item.product.id)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Surface(
            tonalElevation = 8.dp,
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "₩${"%,d".format(total)}",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF4D6BFE)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Checkout")
                }
            }
        }
    }
}

@Composable
private fun CartImageFallback(category: String) {
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
            }
        )
    }
}