package com.example.shopease.repository.interfaces

import com.example.shopease.model.ProductByCategoryItem
import com.example.shopease.model.ResponseCategoriesItem
import okhttp3.Response

interface CategoriesRepository {
    suspend fun loadCategory(): Result<List<ResponseCategoriesItem>>

    suspend fun getProductByCategory(id: Int): Result<List<ProductByCategoryItem>>
}