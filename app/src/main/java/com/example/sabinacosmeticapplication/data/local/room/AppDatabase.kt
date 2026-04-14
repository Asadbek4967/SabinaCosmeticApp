package com.example.sabinacosmeticapplication.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sabinacosmeticapplication.data.local.cart.CartDao
import com.example.sabinacosmeticapplication.data.local.cart.CartEntity
import com.example.sabinacosmeticapplication.data.local.dao.OrderDao
import com.example.sabinacosmeticapplication.data.local.entity.OrderEntity
import com.example.sabinacosmeticapplication.data.local.entity.OrderItemEntity
import com.example.sabinacosmeticapplication.data.local.favorite.FavoriteDao
import com.example.sabinacosmeticapplication.data.local.favorite.FavoriteEntity

@Database(
    entities = [
        CartEntity::class,
        FavoriteEntity::class,
        OrderEntity::class,
        OrderItemEntity::class
    ],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao

    abstract fun favoriteDao(): FavoriteDao

    abstract fun orderDao(): OrderDao
}