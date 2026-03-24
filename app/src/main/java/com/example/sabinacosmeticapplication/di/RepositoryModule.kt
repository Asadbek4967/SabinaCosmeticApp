package com.example.sabinacosmeticapplication.di

import com.example.sabinacosmeticapplication.data.repository.CartRepositoryImpl
import com.example.sabinacosmeticapplication.data.repository.ProductRepositoryImpl
import com.example.sabinacosmeticapplication.domain.repository.CartRepository
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
    @Singleton
    abstract fun bindCartRepository(
        impl: CartRepositoryImpl
    ): CartRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository
}