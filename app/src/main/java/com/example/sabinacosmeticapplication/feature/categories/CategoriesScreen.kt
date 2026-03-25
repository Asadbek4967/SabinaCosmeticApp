package com.example.sabinacosmeticapplication.feature.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

data class CategoryCardUi(
    val title: String,
    val subtitle: String,
    val emoji: String
)

private val CategoryIconBackground = Color(0xFFEFF3FF)
private val CategoriesBackground = Color(0xFFF6F7FB)

val appCategories = listOf(
    CategoryCardUi("Skin Care", "Daily care essentials", "🧴"),
    CategoryCardUi("Serum", "Brightening & hydration", "💧"),
    CategoryCardUi("Sun Care", "SPF protection", "☀️"),
    CategoryCardUi("Cream", "Barrier & moisture", "🫙"),
    CategoryCardUi("Toner", "Fresh prep step", "✨"),
    CategoryCardUi("Cleanser", "Gentle wash", "🫧"),
    CategoryCardUi("Lip Care", "Soft lips", "💄"),
    CategoryCardUi("Ampoule", "Concentrated care", "🩵")
)

@Composable
fun CategoriesScreen(
    padding: PaddingValues,
    onCategoryClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(CategoriesBackground)
            .padding(padding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            CategoriesHeader()
        }

        items(
            items = appCategories,
            key = { it.title }
        ) { item ->
            CategoryCard(
                item = item,
                onClick = { onCategoryClick(item.title) }
            )
        }
    }
}

@Composable
private fun CategoriesHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
    ) {
        Text(
            text = "Categories",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = AppColors.Primary
        )

        Text(
            text = "Browse Korean beauty essentials by product type",
            style = MaterialTheme.typography.bodyMedium,
            color = AppColors.SecondaryText,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
private fun CategoryCard(
    item: CategoryCardUi,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable(onClick = onClick),
        shape = AppShapes.Large,
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(CategoryIconBackground, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.emoji,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Column {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Primary
                )

                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.SecondaryText,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}