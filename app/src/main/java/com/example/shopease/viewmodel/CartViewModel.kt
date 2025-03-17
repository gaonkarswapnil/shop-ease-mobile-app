package com.example.shopease.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.model.AddToCart
import com.example.shopease.model.ProductByCategoryItem
import com.example.shopease.repository.interfaces.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cart: CartRepository
): ViewModel() {

    fun addToCart(product: AddToCart){
        viewModelScope.launch {
            cart.addToCart(product)
        }
    }

    fun removeFromCart(productId: Int){
        viewModelScope.launch {
            cart.removeFromCart(productId)
        }
    }

    fun removeAllItems(){
        viewModelScope.launch {
            cart.removeAllItems()
        }
    }

    suspend fun isProductInCart(id: Int): Boolean{
        return cart.isProductInCart(id)
    }

    private val _addToCart: MutableLiveData<List<AddToCart>> = MutableLiveData()
    val addToCart: LiveData<List<AddToCart>> = _addToCart

    fun getAllItems(){
        viewModelScope.launch {
            _addToCart.postValue(cart.getAllItems())
        }
    }
}