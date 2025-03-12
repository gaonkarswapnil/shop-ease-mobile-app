package com.example.shopease.services.local.converters

import androidx.room.TypeConverter
import com.example.shopease.model.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    private val gson = Gson()

    // Convert List<String> to String
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return gson.toJson(list)
    }

    // Convert String back to List<String>
    @TypeConverter
    fun toStringList(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    // Convert Category object to String
    @TypeConverter
    fun fromCategory(category: Category): String {
        return gson.toJson(category)
    }

    // Convert String back to Category object
    @TypeConverter
    fun toCategory(json: String): Category {
        return gson.fromJson(json, Category::class.java)
    }
}