package com.example.sabinacosmeticapplication.data.local.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items ORDER BY createdAt DESC")
    fun observeCartItems(): Flow<List<CartEntity>>

    @Query("SELECT * FROM cart_items ORDER BY createdAt DESC")
    suspend fun getCartItemsOnce(): List<CartEntity>

    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    suspend fun getCartItemByProductId(productId: String): CartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartEntity)

    @Query("UPDATE cart_items SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateQuantity(productId: String, quantity: Int)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteByProductId(productId: String)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("SELECT COALESCE(SUM(quantity), 0) FROM cart_items")
    fun observeCartBadgeCount(): Flow<Int>
}