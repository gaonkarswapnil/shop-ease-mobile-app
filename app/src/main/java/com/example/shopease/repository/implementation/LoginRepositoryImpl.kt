package com.example.shopease.repository.implementation

import com.example.shopease.model.LoginRequest
import com.example.shopease.model.LoginResponse
import com.example.shopease.repository.interfaces.LoginRepository
import com.example.shopease.services.network.UserApiService
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: UserApiService
): LoginRepository {

    override suspend fun login(login: LoginRequest): Result<LoginResponse> {
        return try{
            val response = apiService.loginUser(login)

            if(response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Login failed"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

}