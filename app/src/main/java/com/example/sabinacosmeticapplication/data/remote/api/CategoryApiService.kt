package com.example.sabinacosmeticapplication.data.remote.api

import com.example.sabinacosmeticapplication.data.remote.dto.CategoryTreeDto
import com.example.sabinacosmeticapplication.data.remote.dto.ProductsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApiService {

    @GET("api/categories/tree")
    suspend fun getCategoryTree(
        @Query("active") active: Boolean = true
    ): List<CategoryTreeDto>

    @GET("api/products")
    suspend fun getCategoryProducts(
        @Query("categoryId") categoryId: String,
        @Query("active") active: Boolean = true,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 50
    ): ProductsResponseDto
}