package com.example.sabinacosmeticapplication.ui.components.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens

@Composable
fun AppSectionTitle(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    trailingText: String? = null,
    titleColor: Color = AppColors.PrimaryText,
    subtitleColor: Color = AppColors.SecondaryText,
    trailingTextColor: Color = AppColors.Primary,
    isCompact: Boolean = false
) {
    val topBottomPadding = if (isCompact) {
        AppDimens.Space8
    } else {
        AppDimens.Space12
    }

    val subtitleTopPadding = if (isCompact) {
        AppDimens.Space4
    } else {
        AppDimens.Space6
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = topBottomPadding)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = titleColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            if (!trailingText.isNullOrBlank()) {
                Text(
                    text = trailingText,
                    modifier = Modifier.padding(start = AppDimens.Space12),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = trailingTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (!subtitle.isNullOrBlank()) {
            Text(
                text = subtitle,
                modifier = Modifier.padding(top = subtitleTopPadding),
                style = MaterialTheme.typography.bodyMedium,
                color = subtitleColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}