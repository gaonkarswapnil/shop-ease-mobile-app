package com.example.shopease.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist_table")
data class ProductByCategoryItem(
    @PrimaryKey(autoGenerate = true)
    val tableId: Int = 0,
    val category: Category,
    val creationAt: String,
    val description: String,
    val id: Int,
    val images: List<String>,
    val price: Long,
    val slug: String,
    val title: String,
    val updatedAt: String
)