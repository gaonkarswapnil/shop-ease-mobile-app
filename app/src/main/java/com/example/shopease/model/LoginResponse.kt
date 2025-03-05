package com.example.shopease.model

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)