package com.example.shopease.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.model.Address
import com.example.shopease.repository.interfaces.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val addressRespository: AddressRepository
): ViewModel() {

    fun insertAddress(address: Address){
        viewModelScope.launch {
            addressRespository.insertAddress(address)
        }
    }

    private val _address: MutableLiveData<List<Address>> = MutableLiveData()
    val address: LiveData<List<Address>> = _address
    fun getAddress(){
        viewModelScope.launch {
            _address.postValue(addressRespository.getAllAddress())
        }
    }

    fun removeAddress(id: Int){
        viewModelScope.launch {
            addressRespository.removeAddress(id)
        }
    }

}