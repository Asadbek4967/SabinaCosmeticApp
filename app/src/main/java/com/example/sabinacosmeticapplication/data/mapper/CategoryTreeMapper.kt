package com.example.sabinacosmeticapplication.data.mapper

import com.example.sabinacosmeticapplication.data.model.AppCategory
import com.example.sabinacosmeticapplication.data.remote.dto.CategoryTreeDto

fun CategoryTreeDto.toAppCategory(): AppCategory {
    val displayTitle = when {
        !nameUz.isNullOrBlank() -> nameUz
        !nameEn.isNullOrBlank() -> nameEn
        !nameRu.isNullOrBlank() -> nameRu
        !nameKo.isNullOrBlank() -> nameKo
        !slug.isNullOrBlank() -> slug
        else -> "Category"
    }

    return AppCategory(
        id = id.orEmpty(),
        title = displayTitle,
        subtitle = resolveSubtitle(displayTitle, slug),
        iconName = resolveIconName(slug, displayTitle),
        slug = slug.orEmpty(),
        parentId = parentId,
        children = children.map { it.toAppCategory() }
    )
}

private fun resolveSubtitle(title: String, slug: String?): String {
    val source = "${title.lowercase()} ${slug.orEmpty().lowercase()}"

    return when {
        "face" in source -> "Cleansers, toners, serums and creams for daily face care."
        "makeup" in source -> "Beauty essentials for base, lips, eyes and finish."
        "hair" in source -> "Shampoo, conditioner and treatments for healthy hair."
        "body" in source -> "Body care products for daily hydration and freshness."
        else -> "Professional cosmetic category."
    }
}

private fun resolveIconName(slug: String?, title: String): String {
    val source = "${slug.orEmpty().lowercase()} ${title.lowercase()}"

    return when {
        "face" in source -> "face"
        "makeup" in source -> "makeup"
        "hair" in source -> "hair"
        "body" in source -> "body"
        "sun" in source -> "sun"
        "cleanser" in source -> "cleanser"
        "toner" in source -> "toner"
        "serum" in source -> "serum"
        "cream" in source -> "cream"
        else -> "category"
    }
}