package com.example.sabinacosmeticapplication.di

import android.content.Context
import androidx.room.Room
import com.example.sabinacosmeticapplication.data.local.cart.AppDatabase
import com.example.sabinacosmeticapplication.data.local.cart.CartDao
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
        appDatabase: AppDatabase
    ): CartDao {
        return appDatabase.cartDao()
    }
}