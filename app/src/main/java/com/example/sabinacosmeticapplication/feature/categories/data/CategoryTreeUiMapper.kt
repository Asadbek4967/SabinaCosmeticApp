package com.example.sabinacosmeticapplication.feature.categories.data

import com.example.sabinacosmeticapplication.data.model.AppCategory
import com.example.sabinacosmeticapplication.data.remote.dto.CategoryTreeDto
import java.util.Locale

object CategoryTreeUiMapper {

    fun mapTree(categories: List<CategoryTreeDto>): List<AppCategory> {
        return categories
            .sortedWith(
                compareBy<CategoryTreeDto> { it.sortOrder ?: Int.MAX_VALUE }
                    .thenBy { it.nameEn.orEmpty() }
            )
            .map { it.toAppCategory() }
    }

    private fun CategoryTreeDto.toAppCategory(): AppCategory {
        val safeName = resolveDisplayName()
        val safeSlug = slug?.trim().orEmpty().ifBlank { safeName.toSlug() }

        val mappedChildren = children
            .sortedWith(
                compareBy<CategoryTreeDto> { it.sortOrder ?: Int.MAX_VALUE }
                    .thenBy { it.nameEn.orEmpty() }
            )
            .map { it.toAppCategory() }

        return AppCategory(
            id = id.orEmpty(),
            title = safeName,
            subtitle = resolveSubtitle(
                slug = safeSlug,
                name = safeName,
                hasChildren = mappedChildren.isNotEmpty(),
            ),
            iconName = resolveIconName(
                slug = safeSlug,
                name = safeName,
            ),
            slug = safeSlug,
            parentId = parentId,
            children = mappedChildren,
        )
    }

    private fun CategoryTreeDto.resolveDisplayName(): String {
        return when {
            !nameUz.isNullOrBlank() -> nameUz.trim()
            !nameEn.isNullOrBlank() -> nameEn.trim()
            !nameRu.isNullOrBlank() -> nameRu.trim()
            !nameKo.isNullOrBlank() -> nameKo.trim()
            !slug.isNullOrBlank() -> slug.trim().replace("-", " ").titleize()
            else -> "Category"
        }
    }

    private fun resolveSubtitle(
        slug: String,
        name: String,
        hasChildren: Boolean,
    ): String {
        val normalized = slug.lowercase(Locale.US)

        return when {
            normalized.contains("face") ->
                "Cleansers, toners, serums and creams for daily face care."

            normalized.contains("hair") ->
                "Shampoo, conditioner and treatments for healthy hair."

            normalized.contains("makeup") ->
                "Beauty essentials for base, lips, eyes and finish."

            normalized.contains("body") ->
                "Body wash, lotion and care products for smooth skin."

            normalized.contains("sun") ->
                "Daily UV protection and skin defense essentials."

            normalized.contains("cleanser") ->
                "Gentle and effective cleansing products."

            normalized.contains("toner") ->
                "Hydrating and balancing toners."

            normalized.contains("serum") ->
                "Targeted concentrated skin treatments."

            normalized.contains("cream") ->
                "Moisturizing creams for skin nourishment."

            normalized.contains("shampoo") ->
                "Scalp cleansing and hair refresh products."

            normalized.contains("conditioner") ->
                "Softening and repairing hair care products."

            normalized.contains("lip") ->
                "Lip color, care and finishing products."

            normalized.contains("foundation") ->
                "Skin base and complexion enhancing products."

            hasChildren ->
                "Explore curated subcategories inside $name."

            else ->
                "Explore professional products in $name."
        }
    }

    private fun resolveIconName(
        slug: String,
        name: String,
    ): String {
        val normalized = slug.lowercase(Locale.US)

        return when {
            normalized.contains("face") -> "face"
            normalized.contains("hair") -> "hair"
            normalized.contains("makeup") -> "makeup"
            normalized.contains("body") -> "body"
            normalized.contains("sun") -> "sun"
            normalized.contains("cleanser") -> "cleanser"
            normalized.contains("toner") -> "toner"
            normalized.contains("serum") -> "serum"
            normalized.contains("cream") -> "cream"
            normalized.contains("shampoo") -> "shampoo"
            normalized.contains("conditioner") -> "conditioner"
            normalized.contains("lip") -> "lip"
            normalized.contains("foundation") -> "foundation"
            else -> name.toSlug()
        }
    }

    private fun String.toSlug(): String {
        return trim()
            .lowercase(Locale.US)
            .replace("&", "and")
            .replace(" ", "-")
            .replace("_", "-")
    }

    private fun String.titleize(): String {
        return split(" ")
            .filter { it.isNotBlank() }
            .joinToString(" ") { part ->
                part.replaceFirstChar { ch ->
                    if (ch.isLowerCase()) ch.titlecase(Locale.US) else ch.toString()
                }
            }
    }
}