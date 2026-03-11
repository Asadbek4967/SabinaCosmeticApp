package com.example.sabinacosmeticapplication.feature.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private data class CategoryCardUi(
    val title: String,
    val subtitle: String,
    val emoji: String
)

@Composable
fun CategoriesScreen(
    padding: PaddingValues
) {
    val categories = listOf(
        CategoryCardUi("Skincare", "Daily care essentials", "🧴"),
        CategoryCardUi("Serum", "Brightening & hydration", "💧"),
        CategoryCardUi("Sun Care", "SPF protection", "☀️"),
        CategoryCardUi("Cream", "Barrier & moisture", "🫙"),
        CategoryCardUi("Toner", "Fresh prep step", "✨"),
        CategoryCardUi("Cleanser", "Gentle wash", "🫧"),
        CategoryCardUi("Lip Care", "Soft lips", "💄"),
        CategoryCardUi("Ampoule", "Concentrated care", "🩵")
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .navigationBarsPadding()
            .background(Color(0xFFF6F7FB))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { item ->
            Card(
                shape = RoundedCornerShape(22.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(Color(0xFFEFF3FF), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = item.emoji)
                    }

                    androidx.compose.foundation.layout.Spacer(
                        modifier = Modifier.padding(top = 10.dp)
                    )

                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = item.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}