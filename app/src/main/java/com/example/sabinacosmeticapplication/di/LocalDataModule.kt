package com.example.sabinacosmeticapplication.di

import android.content.Context
import androidx.room.Room
import com.example.sabinacosmeticapplication.data.local.cart.CartDao
import com.example.sabinacosmeticapplication.data.local.dao.OrderDao
import com.example.sabinacosmeticapplication.data.local.favorite.FavoriteDao
import com.example.sabinacosmeticapplication.data.local.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "sabina_cosmetic_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCartDao(
        database: AppDatabase
    ): CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(
        database: AppDatabase
    ): FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideOrderDao(
        database: AppDatabase
    ): OrderDao {
        return database.orderDao()
    }
}