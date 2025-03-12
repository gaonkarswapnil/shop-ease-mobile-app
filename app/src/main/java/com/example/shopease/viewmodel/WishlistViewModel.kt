package com.example.shopease.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.model.ProductByCategoryItem
import com.example.shopease.repository.interfaces.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val repository: WishlistRepository
): ViewModel() {

    fun addToWishList(product: ProductByCategoryItem){
        viewModelScope.launch {
            repository.addToWishlist(product)
        }
    }

    fun removeFromWishlist(productId: Int){
        viewModelScope.launch {
            repository.removeFromWishList(productId)
        }
    }

    suspend fun isProductAvailable(id: Int): Boolean{
        return repository.isProductInWishlist(id)
    }

    private val _wishlistProduct: MutableLiveData<List<ProductByCategoryItem>> = MutableLiveData<List<ProductByCategoryItem>>()
    val wishlistProduct: LiveData<List<ProductByCategoryItem>> = _wishlistProduct

    fun getProducts(){
        viewModelScope.launch {
            _wishlistProduct.postValue(repository.getWishlistProduct())
        }
    }

}