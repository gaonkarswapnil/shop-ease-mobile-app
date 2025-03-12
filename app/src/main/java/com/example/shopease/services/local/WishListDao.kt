package com.example.shopease.services.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.shopease.model.ProductByCategoryItem

@Dao
interface WishListDao {

    @Insert
    suspend fun insertInToDB(product: ProductByCategoryItem)

    @Query("delete from wishlist_table where id = :productId")
    suspend fun deleteFromDB(productId: Int)

    @Query("select * from wishlist_table")
    suspend fun getAllProduct(): List<ProductByCategoryItem>

    @Query("select * from wishlist_table where id= :id")
    suspend fun isProductAdded(id: Int): ProductByCategoryItem?
}