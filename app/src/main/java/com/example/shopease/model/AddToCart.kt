package com.example.shopease.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "add_to_cart_table")
data class AddToCart (
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