package com.example.sabinacosmeticapplication.data.repository

import com.example.sabinacosmeticapplication.data.mapper.toProduct
import com.example.sabinacosmeticapplication.data.model.AppCategory
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.data.source.CategoryDataSource
import com.example.sabinacosmeticapplication.domain.repository.CategoryRepository
import com.example.sabinacosmeticapplication.feature.categories.data.CategoryTreeUiMapper
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDataSource: CategoryDataSource
) : CategoryRepository {

    override suspend fun getCategoryTree(): List<AppCategory> {
        return CategoryTreeUiMapper.mapTree(
            categoryDataSource.getCategoryTree()
        )
    }

    override suspend fun getProductsByCategoryId(categoryId: String): List<Product> {
        return categoryDataSource.getCategoryProducts(categoryId)
            .map { it.toProduct() }
    }
}