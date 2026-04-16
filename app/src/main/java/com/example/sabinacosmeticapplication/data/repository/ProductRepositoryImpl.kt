package com.example.sabinacosmeticapplication.data.repository

import com.example.sabinacosmeticapplication.data.mapper.toProduct
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.data.remote.source.ProductRemoteDataSource
import com.example.sabinacosmeticapplication.domain.repository.ProductRepository
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    override suspend fun getAllProducts(): List<Product> {
        return safeRemoteCall {
            fetchProducts()
        }
    }

    override suspend fun getProductById(id: String): Product {
        val safeId = id.trim()
        require(safeId.isNotBlank()) { "Product id cannot be blank." }

        return safeRemoteCall {
            remoteDataSource.getProductById(safeId).toProduct()
        }
    }

    override suspend fun getProductsByCategory(categoryId: String): List<Product> {
        val safeCategoryId = categoryId.trim()
        if (safeCategoryId.isBlank()) return emptyList()

        return safeRemoteCall {
            fetchProducts(categoryId = safeCategoryId)
        }
    }

    override suspend fun searchProducts(query: String): List<Product> {
        val normalizedQuery = query.trim()
        if (normalizedQuery.isBlank()) return emptyList()

        return safeRemoteCall {
            fetchProducts(search = normalizedQuery)
        }
    }

    private suspend fun fetchProducts(
        search: String? = null,
        categoryId: String? = null
    ): List<Product> {
        return remoteDataSource.getAllProducts(
            search = search,
            categoryId = categoryId,
            active = true,
            page = DEFAULT_FIRST_PAGE,
            limit = DEFAULT_PRODUCT_FETCH_LIMIT
        ).map { dto ->
            dto.toProduct()
        }
    }

    private suspend inline fun <T> safeRemoteCall(
        crossinline block: suspend () -> T
    ): T {
        return try {
            block()
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            throw IOException(
                "Please check your internet connection and try again.",
                e
            )
        } catch (e: HttpException) {
            throw IllegalStateException(
                "Server error ${e.code()}. Unable to load product data right now.",
                e
            )
        } catch (e: IllegalArgumentException) {
            throw e
        } catch (e: Exception) {
            throw IllegalStateException(
                e.message ?: "Unexpected error occurred while loading product data.",
                e
            )
        }
    }

    private companion object {
        const val DEFAULT_FIRST_PAGE = 1
        const val DEFAULT_PRODUCT_FETCH_LIMIT = 100
    }
}