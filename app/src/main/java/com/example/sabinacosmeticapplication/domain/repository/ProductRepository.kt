package com.example.sabinacosmeticapplication.domain.repository

import com.example.sabinacosmeticapplication.data.model.Product

interface ProductRepository {
    suspend fun getAllProducts(): List<Product>
    suspend fun getProductById(id: String): Product
    suspend fun getRelatedProducts(id: String): List<Product>
    suspend fun getProductsByCategory(categoryId: String): List<Product>
    suspend fun searchProducts(query: String): List<Product>
}