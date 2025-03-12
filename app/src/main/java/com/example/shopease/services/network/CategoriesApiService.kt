package com.example.shopease.services.network

import com.example.shopease.model.ProductByCategoryItem
import com.example.shopease.model.ResponseCategoriesItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoriesApiService {

    @GET("categories/")
    suspend fun getCategories(): Response<List<ResponseCategoriesItem>>

    @GET("categories/{id}/products")
    suspend fun getSingleCategoriesProducts(
        @Path("id") id: Int
    ): Response<List<ProductByCategoryItem>>
}