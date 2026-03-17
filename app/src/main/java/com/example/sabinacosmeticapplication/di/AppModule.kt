package com.example.sabinacosmeticapplication.di

import android.content.Context
import androidx.room.Room
import com.example.sabinacosmeticapplication.data.local.cart.AppDatabase
import com.example.sabinacosmeticapplication.data.local.cart.CartDao
import com.example.sabinacosmeticapplication.data.repository.FakeProductRepository
import com.example.sabinacosmeticapplication.data.repository.ProductRepository
import com.example.sabinacosmeticapplication.feature.cart.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "sabina_cosmetic_db"
        ).build()
    }

    @Provides
    fun provideCartDao(
        database: AppDatabase
    ): CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun provideProductRepository(): ProductRepository {
        return FakeProductRepository()
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        cartDao: CartDao,
        productRepository: ProductRepository
    ): CartRepository {
        return CartRepository(
            cartDao = cartDao,
            productRepository = productRepository
        )
    }
}