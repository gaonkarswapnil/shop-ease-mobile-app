package com.example.shopease.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.model.LoginRequest
import com.example.shopease.model.LoginResponse
import com.example.shopease.repository.interfaces.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: LoginRepository
): ViewModel() {

    private val _loginResponse: MutableLiveData<Result<LoginResponse>> = MutableLiveData<Result<LoginResponse>>()
    val loginResponse: LiveData<Result<LoginResponse>> = _loginResponse

    fun login(request: LoginRequest){
        viewModelScope.launch {
            _loginResponse.postValue(login.login(request))
        }
    }

}