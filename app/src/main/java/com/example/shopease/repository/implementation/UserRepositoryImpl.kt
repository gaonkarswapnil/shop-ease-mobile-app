package com.example.shopease.repository.implementation

import com.example.shopease.model.ImageUploadResponse
import com.example.shopease.model.UserRegistrationRequest
import com.example.shopease.model.UserResponse
import com.example.shopease.repository.interfaces.UserRepository
import com.example.shopease.services.remote.UserApiService
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApiService
) : UserRepository {
    override suspend fun register(user: UserRegistrationRequest): Result<UserResponse> {
        return try {
            val response = userApi.createUser(user)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to register user"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun uploadFileToServer(file: MultipartBody.Part): Result<ImageUploadResponse> {
        return try {
            val response = userApi.uploadImage(file)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to upload image"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
