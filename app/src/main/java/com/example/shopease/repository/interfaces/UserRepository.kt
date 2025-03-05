package com.example.shopease.repository.interfaces

import com.example.shopease.model.ImageUploadResponse
import com.example.shopease.model.UserRegistrationRequest
import com.example.shopease.model.UserResponse
import okhttp3.MultipartBody

interface UserRepository {

    suspend fun register(user: UserRegistrationRequest): Result<UserResponse>
    suspend fun uploadFileToServer( file: MultipartBody.Part) : Result<ImageUploadResponse>
}