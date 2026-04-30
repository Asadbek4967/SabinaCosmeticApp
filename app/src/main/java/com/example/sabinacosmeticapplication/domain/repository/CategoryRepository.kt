package com.example.sabinacosmeticapplication.domain.repository

import com.example.sabinacosmeticapplication.data.model.AppCategory
import com.example.sabinacosmeticapplication.data.model.Product

interface CategoryRepository {
    suspend fun getCategoryTree(): List<AppCategory>
    suspend fun getProductsByCategoryId(categoryId: String): List<Product>
}