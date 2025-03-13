package com.example.shopease.repository.interfaces

import com.example.shopease.model.ProductByCategoryItem

interface ProductDetailRepository {
    suspend fun getSingleProduct(id: Int): Result<ProductByCategoryItem>
}