package com.example.shopease.repository.implementation

import com.example.shopease.model.ProductByCategoryItem
import com.example.shopease.repository.interfaces.WishlistRepository
import com.example.shopease.services.local.WishListDao
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val wishListDao: WishListDao
): WishlistRepository {
    override suspend fun addToWishlist(product: ProductByCategoryItem) {
        wishListDao.insertInToDB(product)
    }

    override suspend fun removeFromWishList(productId: Int) {
        wishListDao.deleteFromDB(productId)
    }

    override suspend fun isProductInWishlist(id: Int): Boolean {
        return wishListDao.isProductAdded(id) != null
    }

    override suspend fun getWishlistProduct(): List<ProductByCategoryItem> {
        return wishListDao.getAllProduct()
    }

}