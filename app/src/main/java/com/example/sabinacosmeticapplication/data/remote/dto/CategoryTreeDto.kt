package com.example.sabinacosmeticapplication.data.remote.dto

data class CategoryTreeDto(
    val id: String? = null,
    val slug: String? = null,
    val nameUz: String? = null,
    val nameRu: String? = null,
    val nameEn: String? = null,
    val nameKo: String? = null,
    val iconUrl: String? = null,
    val bannerUrl: String? = null,
    val parentId: String? = null,
    val children: List<CategoryTreeDto> = emptyList(),
    val level: Int? = null,
    val sortOrder: Int? = null,
    val isLeaf: Boolean? = null,
    val isActive: Boolean? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
)