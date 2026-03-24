package com.example.sabinacosmeticapplication.domain.usecase.product

import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: String): Product {
        return repository.getProductById(id)
    }
}