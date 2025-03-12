package com.example.shopease.repository.interfaces

import com.example.shopease.model.ProductByCategoryItem

interface WishlistRepository {

    suspend fun addToWishlist(product: ProductByCategoryItem)

    suspend fun removeFromWishList(productInt: Int)

    suspend fun isProductInWishlist(id: Int): Boolean

    suspend fun getWishlistProduct(): List<ProductByCategoryItem>

}