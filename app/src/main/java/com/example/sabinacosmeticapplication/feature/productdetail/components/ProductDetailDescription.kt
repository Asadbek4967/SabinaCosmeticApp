package com.example.sabinacosmeticapplication.feature.productdetail.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

private const val CollapsedDescriptionLines = 4

@Composable
fun ProductDetailDescription(
    description: String,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val displayDescription = description.trim().ifBlank { "No description available." }
    val shouldShowToggle = description.trim().isNotBlank()

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = AppShapes.Large,
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppDimens.Space2
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .padding(AppDimens.Space16),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
        ) {
            Text(
                text = displayDescription,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = AppColors.SecondaryText
                ),
                maxLines = if (isExpanded) Int.MAX_VALUE else CollapsedDescriptionLines,
                overflow = TextOverflow.Ellipsis
            )

            if (shouldShowToggle) {
                TextButton(
                    onClick = onToggle,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = if (isExpanded) "Show less" else "Read more",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = AppColors.Primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}