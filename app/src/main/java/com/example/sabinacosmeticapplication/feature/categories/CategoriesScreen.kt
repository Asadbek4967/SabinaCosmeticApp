package com.example.sabinacosmeticapplication.feature.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sabinacosmeticapplication.data.model.AppCategory
import com.example.sabinacosmeticapplication.ui.components.category.CategoryVisualResolver

private val RailWidth = 112.dp
private val TopShortcutHeight = 108.dp
private val ScreenPadding = 18.dp

@Composable
fun CategoriesRoute(
    onCategoryClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(),
    viewModel: CategoryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    CategoriesScreen(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                is CategoryUiAction.OpenCategory -> {
                    onCategoryClick(action.categoryId, action.categoryTitle)
                }

                else -> viewModel.onAction(action)
            }
        },
        modifier = modifier,
        padding = padding,
    )
}

@Composable
fun CategoriesScreen(
    uiState: CategoryUiState,
    onAction: (CategoryUiAction) -> Unit,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(),
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        when {
            uiState.isLoading -> {
                LoadingState(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(padding)
                )
            }

            uiState.errorMessage != null -> {
                ErrorState(
                    message = uiState.errorMessage,
                    onRetry = { onAction(CategoryUiAction.RetryLoad) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(padding)
                )
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(padding)
                        .navigationBarsPadding()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    CategoryHeaderArea(
                        rootCategories = uiState.rootCategories,
                        onCategoryClick = { category ->
                            onAction(CategoryUiAction.SelectRootCategory(category.id))
                        }
                    )

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.42f)
                    )

                    Row(modifier = Modifier.fillMaxSize()) {
                        LeftCategoryRail(
                            categories = uiState.rootCategories,
                            selectedCategoryId = uiState.selectedRootCategoryId,
                            onCategorySelected = { id ->
                                onAction(CategoryUiAction.SelectRootCategory(id))
                            },
                            modifier = Modifier
                                .width(RailWidth)
                                .fillMaxHeight()
                        )

                        CategoryContentPanel(
                            rootCategory = uiState.selectedRootCategory,
                            onOpenCategory = { category ->
                                onAction(
                                    CategoryUiAction.OpenCategory(
                                        categoryId = category.id,
                                        categoryTitle = category.title
                                    )
                                )
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryHeaderArea(
    rootCategories: List<AppCategory>,
    onCategoryClick: (AppCategory) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 16.dp, bottom = 8.dp)
    ) {
        Text(
            text = "카테고리",
            modifier = Modifier.padding(horizontal = ScreenPadding),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(14.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(TopShortcutHeight),
            contentPadding = PaddingValues(horizontal = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(
                items = buildTopShortcuts(rootCategories),
                key = { it.id }
            ) { category ->
                TopShortcutItem(
                    category = category,
                    onClick = { onCategoryClick(category) }
                )
            }
        }
    }
}

@Composable
private fun TopShortcutItem(
    category: AppCategory,
    onClick: () -> Unit,
) {
    val visual = remember(category.iconName, category.title) {
        CategoryVisualResolver.resolve(
            iconName = category.iconName,
            title = category.title
        )
    }

    Column(
        modifier = Modifier
            .width(68.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Surface(
            modifier = Modifier.size(56.dp),
            shape = RoundedCornerShape(18.dp),
            color = visual.backgroundColor
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = visual.icon,
                    contentDescription = category.title,
                    tint = visual.contentColor,
                    modifier = Modifier.size(29.dp)
                )
            }
        }

        Text(
            text = category.title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LeftCategoryRail(
    categories: List<AppCategory>,
    selectedCategoryId: String?,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.62f))
    ) {
        items(
            items = categories,
            key = { it.id }
        ) { category ->
            val selected = category.id == selectedCategoryId

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (selected) {
                            MaterialTheme.colorScheme.background
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0f)
                        }
                    )
                    .clickable { onCategorySelected(category.id) }
                    .padding(vertical = 17.dp)
            ) {
                if (selected) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .width(4.dp)
                            .height(38.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }

                Text(
                    text = category.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 14.dp, end = 10.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun CategoryContentPanel(
    rootCategory: AppCategory?,
    onOpenCategory: (AppCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (rootCategory == null) {
        EmptyCategoryPanel(modifier = modifier)
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 18.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        item {
            CategorySectionTitle(
                title = rootCategory.title,
                showArrow = rootCategory.children.isNotEmpty(),
                onClick = { onOpenCategory(rootCategory) }
            )
        }

        if (rootCategory.children.isEmpty()) {
            item {
                EmptyChildrenCard(rootCategory = rootCategory)
            }
        } else {
            item {
                CategoryGrid(
                    categories = rootCategory.children,
                    onOpenCategory = onOpenCategory,
                    gridHeight = calculateGridHeight(rootCategory.children.size)
                )
            }
        }

        rootCategory.children
            .filter { it.children.isNotEmpty() }
            .forEach { section ->
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.52f)
                        )

                        CategorySectionTitle(
                            title = section.title,
                            showArrow = true,
                            onClick = { onOpenCategory(section) }
                        )

                        CategoryGrid(
                            categories = section.children,
                            onOpenCategory = onOpenCategory,
                            gridHeight = calculateGridHeight(section.children.size)
                        )
                    }
                }
            }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun CategorySectionTitle(
    title: String,
    showArrow: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        if (showArrow) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = ">",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun CategoryGrid(
    categories: List<AppCategory>,
    onOpenCategory: (AppCategory) -> Unit,
    gridHeight: Dp,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .height(gridHeight),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        userScrollEnabled = false
    ) {
        items(
            items = categories,
            key = { it.id }
        ) { category ->
            CategoryGridItem(
                category = category,
                onClick = { onOpenCategory(category) }
            )
        }
    }
}

@Composable
private fun CategoryGridItem(
    category: AppCategory,
    onClick: () -> Unit,
) {
    val visual = remember(category.iconName, category.title) {
        CategoryVisualResolver.resolve(
            iconName = category.iconName,
            title = category.title
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            modifier = Modifier.size(72.dp),
            shape = RoundedCornerShape(21.dp),
            color = visual.backgroundColor
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = visual.icon,
                    contentDescription = category.title,
                    tint = visual.contentColor,
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        Text(
            text = category.title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun EmptyChildrenCard(
    rootCategory: AppCategory,
) {
    val visual = remember(rootCategory.iconName, rootCategory.title) {
        CategoryVisualResolver.resolve(
            iconName = rootCategory.iconName,
            title = rootCategory.title
        )
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(58.dp),
                shape = RoundedCornerShape(18.dp),
                color = visual.backgroundColor
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = visual.icon,
                        contentDescription = null,
                        tint = visual.contentColor,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = rootCategory.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Open this category to see matching products.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun EmptyCategoryPanel(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No category available",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun LoadingState(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.padding(horizontal = 20.dp),
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Outlined.ErrorOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Failed to load categories",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onRetry) {
                    Text(text = "Retry")
                }
            }
        }
    }
}

private fun buildTopShortcuts(
    rootCategories: List<AppCategory>,
): List<AppCategory> {
    val offer = AppCategory(
        id = "shortcut-offer",
        title = "내 할인",
        subtitle = "",
        iconName = "offer",
        slug = "offer",
        parentId = null,
        children = emptyList()
    )

    val rocket = AppCategory(
        id = "shortcut-rocket",
        title = "로켓배송",
        subtitle = "",
        iconName = "rocket",
        slug = "rocket",
        parentId = null,
        children = emptyList()
    )

    return listOf(offer, rocket) + rootCategories.take(8)
}

private fun calculateGridHeight(
    itemCount: Int,
): Dp {
    val rows = ((itemCount + 2) / 3).coerceAtLeast(1)
    return (rows * 118).dp
}