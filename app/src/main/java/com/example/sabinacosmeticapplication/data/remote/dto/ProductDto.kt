package com.example.sabinacosmeticapplication.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductsResponseDto(
    @SerializedName("items")
    val items: List<ProductDto> = emptyList(),

    @SerializedName("total")
    val total: Int? = null,

    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("limit")
    val limit: Int? = null,
)

data class ProductDto(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("categoryId")
    val categoryId: String? = null,

    @SerializedName("slug")
    val slug: String? = null,

    @SerializedName("brand")
    val brand: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("shortDescription")
    val shortDescription: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("skinType")
    val skinType: String? = null,

    @SerializedName("price")
    val price: Double? = null,

    @SerializedName("oldPrice")
    val oldPrice: Double? = null,

    @SerializedName("currency")
    val currency: String? = null,

    @SerializedName("stock")
    val stock: Int? = null,

    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String? = null,

    @SerializedName("imageUrl")
    val imageUrl: String? = null,

    @SerializedName("rating")
    val rating: Double? = null,

    @SerializedName("reviewCount")
    val reviewCount: Int? = null,

    @SerializedName("sortOrder")
    val sortOrder: Int? = null,

    @SerializedName("isFeatured")
    val isFeatured: Boolean? = null,

    @SerializedName("isBestSeller")
    val isBestSeller: Boolean? = null,

    @SerializedName("isNewArrival")
    val isNewArrival: Boolean? = null,

    @SerializedName("isActive")
    val isActive: Boolean? = null,

    @SerializedName("category")
    val category: ProductCategoryDto? = null,
)

data class ProductCategoryDto(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("nameUz")
    val nameUz: String? = null,

    @SerializedName("nameRu")
    val nameRu: String? = null,

    @SerializedName("nameEn")
    val nameEn: String? = null,

    @SerializedName("nameKo")
    val nameKo: String? = null,

    @SerializedName("slug")
    val slug: String? = null,
)

data class ProductLocalizedDto(
    @SerializedName("title")
    val title: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("benefits")
    val benefits: String? = null,

    @SerializedName("howToUse")
    val howToUse: String? = null,

    @SerializedName("ingredients")
    val ingredients: String? = null,

    @SerializedName("warning")
    val warning: String? = null,
)

data class ProductImageDto(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("imageUrl")
    val imageUrl: String? = null,

    @SerializedName("isThumbnail")
    val isThumbnail: Boolean? = null,

    @SerializedName("sortOrder")
    val sortOrder: Int? = null,
)

data class ProductVideoDto(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("videoUrl")
    val videoUrl: String? = null,

    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String? = null,

    @SerializedName("sortOrder")
    val sortOrder: Int? = null,
)