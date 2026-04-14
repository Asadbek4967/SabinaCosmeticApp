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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.ui.components.common.AppSectionTitle
import com.example.sabinacosmeticapplication.ui.components.product.HorizontalProductCard
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.BorderSoft
import com.example.sabinacosmeticapplication.ui.theme.SearchBackground
import com.example.sabinacosmeticapplication.ui.theme.SearchHintText
import com.example.sabinacosmeticapplication.ui.theme.SearchSecondaryText
import com.example.sabinacosmeticapplication.ui.theme.SurfaceWhite
import com.example.sabinacosmeticapplication.ui.theme.TextPrimary

import androidx.compose.foundation.layout.padding

private val SearchAccent = Color(0xFF4D6BFE)
private val SearchAccentSoft = Color(0xFFEFF3FF)
private val SearchChipBackground = Color(0xFFF4F6FB)
private val SearchClearButtonBackground = Color(0xFFF0F2F5)
private val SearchRecentRemoveBackground = Color(0xFFF3F4F6)
private val SearchHeroBackground = Color(0xFFF9FAFF)

private val SearchFieldShape = RoundedCornerShape(20.dp)
private val SearchHeroShape = RoundedCornerShape(24.dp)
private val SearchRecentCardShape = RoundedCornerShape(18.dp)
private val SearchChipShape = RoundedCornerShape(50)
private val SearchTopSafeSpacing = 12.dp

@Composable
fun SearchScreen(
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onClearQuery: () -> Unit,
    onPopularKeywordClick: (String) -> Unit,
    onRecentSearchClick: (String) -> Unit,
    onRemoveRecentSearch: (String) -> Unit,
    onProductClick: (String) -> Unit,
    padding: PaddingValues = PaddingValues(),
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        containerColor = SearchBackground,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SearchBackground)
                .padding(
                    start = AppDimens.ScreenHorizontal,
                    end = AppDimens.ScreenHorizontal,
                    top = innerPadding.calculateTopPadding() +
                            padding.calculateTopPadding() +
                            SearchTopSafeSpacing,
                    bottom = innerPadding.calculateBottomPadding() +
                            padding.calculateBottomPadding()
                )
        ) {
            SearchHeader()

            Spacer(modifier = Modifier.height(AppDimens.Space18))

            SearchInputBar(
                query = uiState.query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                onClearQuery = onClearQuery
            )

            Spacer(modifier = Modifier.height(AppDimens.Space18))

            when {
                uiState.isLoading -> {
                    SearchLoadingState()
                }

                uiState.showValidationState -> {
                    SearchValidationState(
                        message = uiState.validationMessage.orEmpty()
                    )
                }

                uiState.showErrorState -> {
                    SearchErrorState(
                        message = uiState.errorMessage.orEmpty()
                    )
                }

                uiState.showResults -> {
                    SearchResultContent(
                        query = uiState.normalizedQuery,
                        results = uiState.results,
                        onProductClick = onProductClick
                    )
                }

                uiState.showNoResultsState -> {
                    SearchEmptyState(query = uiState.normalizedQuery)
                }

                else -> {
                    SearchDiscoveryContent(
                        recentSearches = uiState.recentSearches,
                        popularKeywords = uiState.popularKeywords,
                        onRecentSearchClick = onRecentSearchClick,
                        onRemoveRecentSearch = onRemoveRecentSearch,
                        onPopularKeywordClick = onPopularKeywordClick
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchHeader() {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
    ) {
        Column {
            Text(
                text = "Search",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Text(
                text = "Find Korean skincare, makeup, and daily beauty essentials curated for you.",
                style = MaterialTheme.typography.bodyMedium,
                color = SearchSecondaryText,
                modifier = Modifier.padding(top = AppDimens.Space4)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = SearchHeroShape,
            colors = CardDefaults.cardColors(
                containerColor = SearchHeroBackground
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = AppDimens.Space16,
                        vertical = AppDimens.Space16
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(SearchAccentSoft)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        tint = SearchAccent
                    )
                }

                Spacer(modifier = Modifier.width(AppDimens.Space12))

                Column {
                    Text(
                        text = "Discover faster",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )

                    Text(
                        text = "Search by product name, brand, ingredient, or category.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SearchSecondaryText,
                        modifier = Modifier.padding(top = AppDimens.Space4)
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchInputBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onClearQuery: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "Search serum, cleanser, sunscreen...",
                style = MaterialTheme.typography.bodyMedium,
                color = SearchHintText
            )
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = TextPrimary),
        singleLine = true,
        shape = SearchFieldShape,
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
                        .clip(CircleShape)
                        .background(SearchClearButtonBackground)
                        .clickable(onClick = onClearQuery)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear search",
                        tint = SearchSecondaryText
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch(query) }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = SurfaceWhite,
            unfocusedContainerColor = SurfaceWhite,
            disabledContainerColor = SurfaceWhite,
            focusedBorderColor = SearchAccent,
            unfocusedBorderColor = BorderSoft,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
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
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space20),
        contentPadding = PaddingValues(bottom = AppDimens.Space24)
    ) {
        if (recentSearches.isNotEmpty()) {
            item {
                AppSectionTitle(
                    title = "Recent Searches",
                    subtitle = "Quickly continue from where you left off",
                    titleColor = TextPrimary,
                    subtitleColor = SearchSecondaryText
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
            AppSectionTitle(
                title = "Trending Now",
                subtitle = "Popular beauty keywords shoppers are exploring",
                titleColor = TextPrimary,
                subtitleColor = SearchSecondaryText
            )
        }

        item {
            PopularKeywordSection(
                keywords = popularKeywords,
                onKeywordClick = onPopularKeywordClick
            )
        }

        item {
            Spacer(
                modifier = Modifier.windowInsetsBottomHeight(
                    WindowInsets.safeDrawing
                )
            )
        }
    }
}

@Composable
private fun SearchResultContent(
    query: String,
    results: List<Product>,
    onProductClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space14),
        contentPadding = PaddingValues(bottom = AppDimens.Space24)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = AppDimens.Space16,
                        vertical = AppDimens.Space14
                    )
                ) {
                    Text(
                        text = "Results for \"$query\"",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = if (results.size == 1) {
                            "1 product found"
                        } else {
                            "${results.size} products found"
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = SearchSecondaryText,
                        modifier = Modifier.padding(top = AppDimens.Space4)
                    )
                }
            }
        }

        items(
            items = results,
            key = { it.id }
        ) { product ->
            HorizontalProductCard(
                product = product.toDisplayProduct(),
                onClick = { onProductClick(product.id) }
            )
        }

        item {
            Spacer(
                modifier = Modifier.windowInsetsBottomHeight(
                    WindowInsets.safeDrawing
                )
            )
        }
    }
}

@Composable
private fun RecentSearchSection(
    recentSearches: List<String>,
    onRecentSearchClick: (String) -> Unit,
    onRemoveRecentSearch: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space10)
    ) {
        recentSearches.forEach { keyword ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = SearchRecentCardShape,
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onRecentSearchClick(keyword) }
                        .padding(
                            horizontal = AppDimens.Space14,
                            vertical = AppDimens.Space14
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(SearchAccentSoft)
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.History,
                            contentDescription = null,
                            tint = SearchAccent
                        )
                    }

                    Text(
                        text = keyword,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = AppDimens.Space10),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(SearchRecentRemoveBackground)
                            .clickable { onRemoveRecentSearch(keyword) }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove recent search",
                            tint = SearchSecondaryText
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
        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space10),
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space10)
    ) {
        keywords.forEach { keyword ->
            Surface(
                modifier = Modifier.clickable { onKeywordClick(keyword) },
                shape = SearchChipShape,
                color = SearchChipBackground
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = AppDimens.Space14,
                        vertical = AppDimens.Space10
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.TrendingUp,
                        contentDescription = null,
                        tint = SearchAccent
                    )

                    Spacer(modifier = Modifier.width(AppDimens.Space8))

                    Text(
                        text = keyword,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchLoadingState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = AppDimens.Space32),
        contentAlignment = Alignment.TopCenter
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = AppDimens.Space24,
                    vertical = AppDimens.Space24
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = SearchAccent)

                Spacer(modifier = Modifier.height(AppDimens.Space12))

                Text(
                    text = "Searching products...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SearchSecondaryText
                )
            }
        }
    }
}

@Composable
private fun SearchValidationState(
    message: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = AppDimens.Space32),
        contentAlignment = Alignment.TopCenter
    ) {
        SearchStateCard(
            title = "Type a little more",
            message = message
        )
    }
}

@Composable
private fun SearchErrorState(
    message: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = AppDimens.Space32),
        contentAlignment = Alignment.TopCenter
    ) {
        SearchStateCard(
            title = "Something went wrong",
            message = message
        )
    }
}

@Composable
private fun SearchEmptyState(
    query: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = AppDimens.Space32),
        contentAlignment = Alignment.TopCenter
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = AppDimens.Space24,
                    vertical = AppDimens.Space24
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🔎",
                    style = MaterialTheme.typography.displaySmall
                )

                Spacer(modifier = Modifier.height(AppDimens.Space12))

                Text(
                    text = "No results for \"$query\"",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(AppDimens.Space6))

                Text(
                    text = "Try another product name, ingredient, brand, or category.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SearchSecondaryText,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun SearchStateCard(
    title: String,
    message: String
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = AppDimens.Space24,
                vertical = AppDimens.Space24
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(AppDimens.Space8))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = SearchSecondaryText,
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun Product.toDisplayProduct(): Product {
    return copy(
        title = safeTitle,
        brand = safeBrand,
        category = normalizedCategory,
        price = formattedPrice,
        oldPrice = formattedOldPrice,
        description = safeDescription,
        imageUrl = imageUrl.trim()
    )
}