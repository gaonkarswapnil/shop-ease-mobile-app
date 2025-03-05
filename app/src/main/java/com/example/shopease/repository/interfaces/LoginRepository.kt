package com.example.shopease.repository.interfaces

import com.example.shopease.model.LoginRequest
import com.example.shopease.model.LoginResponse

interface LoginRepository {

    suspend fun login(login: LoginRequest): Result<LoginResponse>

}