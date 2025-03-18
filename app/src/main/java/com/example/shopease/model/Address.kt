package com.example.shopease.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_table")
data class Address (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val address: String,
    val landmark: String,
    val city: String,
    val state: String,
    val zipCode: Int,
    val country: String
)