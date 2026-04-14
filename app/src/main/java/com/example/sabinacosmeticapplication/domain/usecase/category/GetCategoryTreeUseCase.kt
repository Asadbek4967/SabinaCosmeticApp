package com.example.sabinacosmeticapplication.domain.usecase.category

import com.example.sabinacosmeticapplication.data.model.AppCategory
import com.example.sabinacosmeticapplication.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryTreeUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): List<AppCategory> {
        return categoryRepository.getCategoryTree()
    }
}