package com.example.sabinacosmeticapplication.feature.productdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun ProductDetailLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(AppColors.Background)
            .padding(
                horizontal = AppDimens.ScreenHorizontal,
                vertical = AppDimens.Space16
            )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space20)
        ) {
            Card(
                shape = AppShapes.ExtraLarge,
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.Surface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = AppDimens.CardElevation
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppDimens.Space20),
                    verticalArrangement = Arrangement.spacedBy(AppDimens.Space18)
                ) {
                    LoadingBlock(
                        widthFraction = 0.28f,
                        height = AppDimens.Space32
                    )

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingBlock(
                            widthFraction = 0.68f,
                            height = AppDimens.ProductImageXL
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space14)
            ) {
                LoadingBlock(
                    widthFraction = 0.32f,
                    height = AppDimens.Space16
                )

                LoadingBlock(
                    widthFraction = 0.72f,
                    height = AppDimens.Space28
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.Space8)
                ) {
                    LoadingBlock(
                        widthFraction = 0.26f,
                        height = AppDimens.Space24
                    )
                    LoadingBlock(
                        widthFraction = 0.22f,
                        height = AppDimens.Space24
                    )
                }
            }

            Card(
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
                        .padding(AppDimens.Space16),
                    verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
                ) {
                    LoadingBlock(
                        widthFraction = 0.44f,
                        height = AppDimens.Space16
                    )
                    LoadingBlock(
                        widthFraction = 0.94f,
                        height = AppDimens.Space14
                    )
                    LoadingBlock(
                        widthFraction = 0.88f,
                        height = AppDimens.Space14
                    )
                    LoadingBlock(
                        widthFraction = 0.66f,
                        height = AppDimens.Space14
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = AppColors.Primary,
                    modifier = Modifier.size(AppDimens.IconExtraLarge)
                )
            }
        }
    }
}

@Composable
private fun LoadingBlock(
    widthFraction: Float,
    height: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(widthFraction.coerceIn(0f, 1f))
            .height(height)
            .background(
                color = AppColors.SurfaceVariant,
                shape = RoundedCornerShape(AppDimens.Space12)
            )
    )
}