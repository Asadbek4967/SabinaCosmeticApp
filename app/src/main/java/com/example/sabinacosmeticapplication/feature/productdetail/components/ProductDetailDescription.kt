package com.example.sabinacosmeticapplication.feature.productdetail.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun ProductDetailDescription(
    product: Product,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
    ) {
        DetailSectionCard(
            title = "Description",
            content = product.description
        )

        product.skinType?.let {
            DetailSectionCard(
                title = "Skin type",
                content = it
            )
        }

        product.benefits?.let {
            DetailSectionCard(
                title = "Benefits",
                content = it
            )
        }

        product.howToUse?.let {
            DetailSectionCard(
                title = "How to use",
                content = it
            )
        }

        product.ingredients?.let {
            DetailSectionCard(
                title = "Ingredients",
                content = it
            )
        }

        product.warning?.let {
            DetailSectionCard(
                title = "Warning",
                content = it
            )
        }

        if (product.videos.isNotEmpty()) {
            ProductVideoSection(product = product)
        }
    }
}

@Composable
private fun DetailSectionCard(
    title: String,
    content: String,
) {
    if (content.isBlank()) return

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = AppShapes.ExtraLarge,
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.CardElevation)
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.Space16)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AppColors.Primary
            )

            Spacer(modifier = Modifier.height(AppDimens.Space8))

            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.Primary
            )
        }
    }
}

@Composable
private fun ProductVideoSection(
    product: Product,
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = AppShapes.ExtraLarge,
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.CardElevation)
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.Space16),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
        ) {
            Text(
                text = "Videos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AppColors.Primary
            )

            product.videos.forEach { video ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = AppShapes.Large,
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.Background
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(AppDimens.Space14)
                    ) {
                        Text(
                            text = video.title.ifBlank { "Product video" },
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppColors.Primary
                        )

                        Spacer(modifier = Modifier.height(AppDimens.Space6))

                        Text(
                            text = video.videoUrl,
                            style = MaterialTheme.typography.bodySmall,
                            color = AppColors.SecondaryText
                        )

                        Spacer(modifier = Modifier.height(AppDimens.Space8))

                        TextButton(
                            onClick = {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(video.videoUrl)
                                )
                                context.startActivity(intent)
                            }
                        ) {
                            Text("Open video")
                        }
                    }
                }
            }
        }
    }
}