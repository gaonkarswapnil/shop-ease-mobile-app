package com.example.shopease.services.remote

import com.example.shopease.model.ImageUploadResponse
import com.example.shopease.model.UserRegistrationRequest
import com.example.shopease.model.UserResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UserApiService {

    @POST("users/")
    suspend fun createUser(
        @Body userRegistrationRequest: UserRegistrationRequest
    ): Response<UserResponse>

    @Multipart
    @POST("files/upload")
    suspend fun uploadImage(@Part file: MultipartBody.Part): Response<ImageUploadResponse>
}
