package com.example.shopease.repository.implementation

import com.example.shopease.model.ProductByCategoryItem
import com.example.shopease.repository.interfaces.ProductDetailRepository
import com.example.shopease.services.network.CategoriesApiService
import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(
    private val apiService: CategoriesApiService
): ProductDetailRepository {
    override suspend fun getSingleProduct(id: Int): Result<ProductByCategoryItem> {
        return try{
            val response = apiService.getSingleProduct(id)
            if(response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Failed to retrive details"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}