package com.example.sabinacosmeticapplication.data.remote.api

import com.example.sabinacosmeticapplication.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {

    @GET("products")
    suspend fun getAllProducts(): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: String
    ): ProductDto
}