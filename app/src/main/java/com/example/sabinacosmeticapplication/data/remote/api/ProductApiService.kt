package com.example.sabinacosmeticapplication.data.remote.api

import com.example.sabinacosmeticapplication.data.remote.dto.ProductDetailDto
import com.example.sabinacosmeticapplication.data.remote.dto.ProductsResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {

    @GET("api/products")
    suspend fun getAllProducts(
        @Query("search") search: String? = null,
        @Query("categoryId") categoryId: String? = null,
        @Query("featured") featured: Boolean? = null,
        @Query("active") active: Boolean = true,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE
    ): ProductsResponseDto

    @GET("api/products/{id}")
    suspend fun getProductById(
        @Path("id") id: String,
        @Query("active") active: Boolean = true
    ): ProductDetailDto

    companion object {
        const val DEFAULT_PAGE_SIZE = 50
    }
}