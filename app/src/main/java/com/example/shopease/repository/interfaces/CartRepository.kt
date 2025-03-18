package com.example.shopease.repository.interfaces

import androidx.lifecycle.LiveData
import com.example.shopease.model.AddToCart
import com.example.shopease.model.ProductByCategoryItem

interface CartRepository {

    suspend fun addToCart(product: AddToCart)

    suspend fun removeFromCart(productId: Int)

    suspend fun removeAllItems()

    suspend fun getAllItems(): List<AddToCart>

    suspend fun isProductInCart(id: Int): Boolean

    suspend fun updateCart(product: AddToCart)

}