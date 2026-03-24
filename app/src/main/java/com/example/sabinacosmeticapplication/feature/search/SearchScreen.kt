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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

private val SearchAccent = Color(0xFF4D6BFE)
private val SearchChipBackground = Color(0xFFF3F6FB)
private val SearchClearButtonBackground = Color(0xFFF0F2F5)
private val RecentRemoveBackground = Color(0xFFF3F4F6)

private val SearchFieldShape = RoundedCornerShape(20.dp)
private val SearchRecentCardShape = RoundedCornerShape(18.dp)
private val SearchChipShape = RoundedCornerShape(50)

@Composable
fun SearchScreen(
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onClearQuery: () -> Unit,
    onPopularKeywordClick: (String) -> Unit,
    onRemoveRecentSearch: (String) -> Unit,
    onProductClick: (String) -> Unit,
    padding: PaddingValues = PaddingValues(),
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        containerColor = SearchBackground,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SearchBackground)
                .padding(innerPadding)
                .padding(padding)
                .padding(
                    horizontal = AppDimens.ScreenHorizontal,
                    vertical = AppDimens.Space14
                )
        ) {
            SearchHeader()

            Spacer(modifier = Modifier.height(AppDimens.Space16))

            SearchInputBar(
                query = uiState.query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                onClearQuery = onClearQuery
            )

            Spacer(modifier = Modifier.height(AppDimens.Space16))

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
            modifier = Modifier.padding(top = AppDimens.Space4)
        )
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
        onValueChange = { value ->
            onQueryChange(value)
            onSearch(value)
        },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "Search skincare, serum, cream...",
                style = MaterialTheme.typography.bodyMedium,
                color = SearchHintText
            )
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = TextPrimary),
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
                        .size(AppDimens.SearchClearButtonSize)
                        .clip(CircleShape)
                        .background(SearchClearButtonBackground)
                        .clickable(onClick = onClearQuery),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear search",
                        tint = SearchSecondaryText,
                        modifier = Modifier.size(AppDimens.SearchClearIconSize)
                    )
                }
            }
        },
        singleLine = true,
        shape = SearchFieldShape,
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
                    subtitle = "Your recently searched keywords",
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
                title = "Popular Keywords",
                subtitle = "Trending categories you may like",
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
    }
}

@Composable
private fun SearchResultContent(
    results: List<Product>,
    onProductClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space14),
        contentPadding = PaddingValues(bottom = AppDimens.Space24)
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
            HorizontalProductCard(
                product = product,
                onClick = { onProductClick(product.id) }
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
                elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.Space2)
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
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        tint = SearchSecondaryText,
                        modifier = Modifier.size(AppDimens.SearchRecentLeadingIconSize)
                    )

                    Text(
                        text = keyword,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = AppDimens.Space10),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary
                    )

                    Box(
                        modifier = Modifier
                            .size(AppDimens.SearchRecentRemoveButtonSize)
                            .clip(CircleShape)
                            .background(RecentRemoveBackground)
                            .clickable { onRemoveRecentSearch(keyword) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove recent search",
                            tint = SearchSecondaryText,
                            modifier = Modifier.size(AppDimens.SearchRecentRemoveIconSize)
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
                Text(
                    text = keyword,
                    modifier = Modifier.padding(
                        horizontal = AppDimens.Space16,
                        vertical = AppDimens.Space10
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
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

            Spacer(modifier = Modifier.height(AppDimens.Space12))

            Text(
                text = "No results for \"$query\"",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(AppDimens.Space6))

            Text(
                text = "Try another product name, brand, or category",
                style = MaterialTheme.typography.bodyMedium,
                color = SearchSecondaryText
            )
        }
    }
}