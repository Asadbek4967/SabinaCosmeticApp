package com.example.sabinacosmeticapplication.domain.usecase.category

import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryProductsUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: String): List<Product> {
        return categoryRepository.getProductsByCategoryId(categoryId)
    }
}