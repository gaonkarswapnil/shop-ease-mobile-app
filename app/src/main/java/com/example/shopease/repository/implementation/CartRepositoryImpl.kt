package com.example.shopease.repository.implementation

import com.example.shopease.model.AddToCart
import com.example.shopease.model.ProductByCategoryItem
import com.example.shopease.repository.interfaces.CartRepository
import com.example.shopease.services.local.AddToCartDao
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val addToCartDao: AddToCartDao
): CartRepository {
    override suspend fun addToCart(product: AddToCart) {
        addToCartDao.addToCart(product)
    }

    override suspend fun removeFromCart(productId: Int) {
        addToCartDao.removeFromCart(productId)
    }

    override suspend fun removeAllItems() {
        addToCartDao.removeAllItems()
    }

    override suspend fun getAllItems(): List<AddToCart> {
        return addToCartDao.getAllItems()
    }

    override suspend fun isProductInCart(id: Int): Boolean {
        return addToCartDao.isAvailable(id) != null
    }

    override suspend fun updateCart(product: AddToCart) {
        return addToCartDao.updateCart(product)
    }

}