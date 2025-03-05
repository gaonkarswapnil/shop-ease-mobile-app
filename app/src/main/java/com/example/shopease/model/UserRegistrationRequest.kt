package com.example.shopease.model

data class UserRegistrationRequest(
    val avatar: String,
    val email: String,
    val name: String,
    val password: String
)