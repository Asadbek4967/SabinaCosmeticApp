package com.example.sabinacosmeticapplication.di

import com.example.sabinacosmeticapplication.data.repository.AuthRepositoryImpl
import com.example.sabinacosmeticapplication.data.repository.CartRepositoryImpl
import com.example.sabinacosmeticapplication.data.repository.CategoryRepositoryImpl
import com.example.sabinacosmeticapplication.data.repository.FavoriteRepositoryImpl
import com.example.sabinacosmeticapplication.data.repository.OrderRepositoryImpl
import com.example.sabinacosmeticapplication.data.repository.ProductRepositoryImpl
import com.example.sabinacosmeticapplication.domain.repository.AuthRepository
import com.example.sabinacosmeticapplication.domain.repository.CartRepository
import com.example.sabinacosmeticapplication.domain.repository.CategoryRepository
import com.example.sabinacosmeticapplication.domain.repository.FavoriteRepository
import com.example.sabinacosmeticapplication.domain.repository.OrderRepository
import com.example.sabinacosmeticapplication.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        impl: CartRepositoryImpl
    ): CartRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(
        impl: FavoriteRepositoryImpl
    ): FavoriteRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        impl: OrderRepositoryImpl
    ): OrderRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository
}