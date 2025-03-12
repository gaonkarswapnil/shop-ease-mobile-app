package com.example.shopease.interfaces

import com.example.shopease.model.ProductByCategoryItem
import com.example.shopease.model.ResponseCategoriesItem

interface OnProductClick {
    fun onProductClick(product: ProductByCategoryItem)
}