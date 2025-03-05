package com.example.shopease.model

import java.io.Serializable

data class UserResponse(
    val avatar: String,
    val creationAt: String,
    val email: String,
    val id: Int,
    val name: String,
    val password: String,
    val role: String,
    val updatedAt: String
) : Serializable