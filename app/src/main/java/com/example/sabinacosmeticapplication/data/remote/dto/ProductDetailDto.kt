package com.example.sabinacosmeticapplication.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductDetailDto(
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

    @SerializedName("localized")
    val localized: ProductLocalizedDto? = null,

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

    @SerializedName("images")
    val images: List<ProductImageDto> = emptyList(),

    @SerializedName("videos")
    val videos: List<ProductVideoDto> = emptyList(),

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null,
)