package com.example.shopease.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.shopease.model.ProductByCategoryItem
import com.example.shopease.model.ResponseCategoriesItem
import com.example.shopease.repository.interfaces.CategoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoriesRepository: CategoriesRepository
): ViewModel() {

    private val _categories: MutableLiveData<Result<List<ResponseCategoriesItem>>> = MutableLiveData<Result<List<ResponseCategoriesItem>>>()
    val categories: LiveData<Result<List<ResponseCategoriesItem>>> = _categories

    fun fetchCategories(){
        viewModelScope.launch {
            _categories.postValue(categoriesRepository.loadCategory())
        }
    }

    private val _categoryByProduct: MutableLiveData<Result<List<ProductByCategoryItem>>> = MutableLiveData<Result<List<ProductByCategoryItem>>>()
    val categoryByProduct: LiveData<Result<List<ProductByCategoryItem>>> = _categoryByProduct

    fun fetchProductsByCategory(id: Int){
        viewModelScope.launch {
            _categoryByProduct.postValue(categoriesRepository.getProductByCategory(id))
        }
    }
}