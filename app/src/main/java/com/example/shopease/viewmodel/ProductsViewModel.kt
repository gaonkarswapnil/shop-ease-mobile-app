package com.example.shopease.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.model.ProductByCategoryItem
import com.example.shopease.repository.interfaces.ProductDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productDetails: ProductDetailRepository
): ViewModel() {

    private val _singleProduct: MutableLiveData<Result<ProductByCategoryItem>> = MutableLiveData()
    val singleProduct: LiveData<Result<ProductByCategoryItem>> = _singleProduct

    fun getSingleProduct(id: Int){
        viewModelScope.launch {
            _singleProduct.postValue(productDetails.getSingleProduct(id))
        }
    }

}