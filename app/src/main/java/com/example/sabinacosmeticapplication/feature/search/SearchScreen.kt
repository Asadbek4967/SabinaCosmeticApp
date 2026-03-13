package com.example.sabinacosmeticapplication.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import coil.compose.SubcomposeAsyncImage
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.ui.theme.BorderSoft
import com.example.sabinacosmeticapplication.ui.theme.FlashSaleColor
import com.example.sabinacosmeticapplication.ui.theme.SearchBackground
import com.example.sabinacosmeticapplication.ui.theme.SearchHintText
import com.example.sabinacosmeticapplication.ui.theme.SearchSecondaryText
import com.example.sabinacosmeticapplication.ui.theme.SurfaceWhite
import com.example.sabinacosmeticapplication.ui.theme.TextPrimary

private val SearchAccent = Color(0xFF4D6BFE)
private val SearchImageBackground = Color(0xFFF1F4FB)
private val SearchChipBackground = Color(0xFFF3F6FB)

private val BestSellerBackground = Color(0xFFFFF1E8)
private val BestSellerText = Color(0xFFFF7A00)

private val FlashSaleBackground = Color(0xFFFFEEF1)

@Composable
fun SearchScreen(
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onClearQuery: () -> Unit,
    onPopularKeywordClick: (String) -> Unit,
    onRemoveRecentSearch: (String) -> Unit,
    onProductClick: (String) -> Unit,
    padding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SearchBackground)
            .padding(padding)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        SearchHeader()
        Spacer(modifier = Modifier.height(16.dp))

        SearchInputBar(
            query = uiState.query,
            onQueryChange = onQueryChange,
            onClearQuery = onClearQuery
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> {
                SearchLoadingState()
            }

            uiState.query.isBlank() -> {
                SearchDiscoveryContent(
                    recentSearches = uiState.recentSearches,
                    popularKeywords = uiState.popularKeywords,
                    onRecentSearchClick = onSearch,
                    onRemoveRecentSearch = onRemoveRecentSearch,
                    onPopularKeywordClick = onPopularKeywordClick
                )
            }

            uiState.results.isNotEmpty() -> {
                SearchResultContent(
                    results = uiState.results,
                    onProductClick = onProductClick
                )
            }

            else -> {
                SearchEmptyState(query = uiState.query)
            }
        }
    }
}

@Composable
private fun SearchHeader() {
    Column {
        Text(
            text = "Search",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Text(
            text = "Find your favorite Korean skincare products",
            style = MaterialTheme.typography.bodyMedium,
            color = SearchSecondaryText,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
private fun SearchInputBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "Search skincare, serum, cream...",
                style = MaterialTheme.typography.bodyMedium,
                color = SearchHintText
            )
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = TextPrimary
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = SearchSecondaryText
            )
        },
        trailingIcon = {
            if (query.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF0F2F5))
                        .clickable { onClearQuery() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear search",
                        tint = SearchSecondaryText,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = SurfaceWhite,
            unfocusedContainerColor = SurfaceWhite,
            disabledContainerColor = SurfaceWhite,
            focusedBorderColor = SearchAccent,
            unfocusedBorderColor = BorderSoft,
            cursorColor = SearchAccent
        )
    )
}

@Composable
private fun SearchDiscoveryContent(
    recentSearches: List<String>,
    popularKeywords: List<String>,
    onRecentSearchClick: (String) -> Unit,
    onRemoveRecentSearch: (String) -> Unit,
    onPopularKeywordClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        if (recentSearches.isNotEmpty()) {
            item {
                SectionTitle(
                    title = "Recent Searches",
                    subtitle = "Your recently searched keywords"
                )
            }

            item {
                RecentSearchSection(
                    recentSearches = recentSearches,
                    onRecentSearchClick = onRecentSearchClick,
                    onRemoveRecentSearch = onRemoveRecentSearch
                )
            }
        }

        item {
            SectionTitle(
                title = "Popular Keywords",
                subtitle = "Trending categories you may like"
            )
        }

        item {
            PopularKeywordSection(
                keywords = popularKeywords,
                onKeywordClick = onPopularKeywordClick
            )
        }
    }
}

@Composable
private fun SearchResultContent(
    results: List<Product>,
    onProductClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Text(
                text = if (results.size == 1) "1 result found" else "${results.size} results found",
                style = MaterialTheme.typography.bodyMedium,
                color = SearchSecondaryText,
                fontWeight = FontWeight.Medium
            )
        }

        items(
            items = results,
            key = { it.id }
        ) { product ->
            SearchProductItem(
                product = product,
                onClick = { onProductClick(product.id) }
            )
        }
    }
}

@Composable
private fun SectionTitle(
    title: String,
    subtitle: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = SearchSecondaryText
        )
    }
}

@Composable
private fun RecentSearchSection(
    recentSearches: List<String>,
    onRecentSearchClick: (String) -> Unit,
    onRemoveRecentSearch: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        recentSearches.forEach { keyword ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onRecentSearchClick(keyword) }
                        .padding(horizontal = 14.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        tint = SearchSecondaryText,
                        modifier = Modifier.size(18.dp)
                    )

                    Text(
                        text = keyword,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary
                    )

                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF3F4F6))
                            .clickable { onRemoveRecentSearch(keyword) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove recent search",
                            tint = SearchSecondaryText,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PopularKeywordSection(
    keywords: List<String>,
    onKeywordClick: (String) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        keywords.forEach { keyword ->
            Surface(
                modifier = Modifier.clickable { onKeywordClick(keyword) },
                shape = RoundedCornerShape(50),
                color = SearchChipBackground
            ) {
                Text(
                    text = keyword,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
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
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(112.dp)
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
                            textColor = FlashSaleColor
                        )
                    }
                }

                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
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
                                color = FlashSaleColor,
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
private fun SearchLoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = SearchAccent)
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
                text = "No results for \"$query\"",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Try another product name, brand, or category",
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