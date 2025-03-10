package com.example.shopease.repository.implementation

import com.example.shopease.model.ProductByCategoryItem
import com.example.shopease.model.ResponseCategoriesItem
import com.example.shopease.repository.interfaces.CategoriesRepository
import com.example.shopease.services.remote.CategoriesApiService
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val apiService: CategoriesApiService
): CategoriesRepository {
    override suspend fun loadCategory(): Result<List<ResponseCategoriesItem>> {
        return try{
            val response = apiService.getCategories()
            if(response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Failed to load categories"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getProductByCategory(id: Int): Result<List<ProductByCategoryItem>> {
        return try{
            val response = apiService.getSingleCategoriesProducts(id)
            if(response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Failed to load categories"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}