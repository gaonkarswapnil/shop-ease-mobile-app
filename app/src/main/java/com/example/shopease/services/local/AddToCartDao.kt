package com.example.shopease.services.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shopease.model.AddToCart

@Dao
interface AddToCartDao {

    @Insert
    suspend fun addToCart(product: AddToCart)

    @Query("select * from add_to_cart_table")
    suspend fun getAllItems(): List<AddToCart>

    @Query("delete from add_to_cart_table")
    suspend fun removeAllItems()

    @Query("delete from add_to_cart_table where id = :productId")
    suspend fun removeFromCart(productId: Int)

    @Query("select * from add_to_cart_table where id= :id")
    suspend fun isAvailable(id: Int): AddToCart?

    @Update
    suspend fun updateCart(product: AddToCart)

}