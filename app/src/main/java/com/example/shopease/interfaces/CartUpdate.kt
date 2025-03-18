package com.example.shopease.interfaces

import com.example.shopease.model.AddToCart

interface CartUpdate {
    fun onClick(addToCart: AddToCart)
}