package com.example.shopease.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.model.ImageUploadResponse
import com.example.shopease.model.UserRegistrationRequest
import com.example.shopease.model.UserResponse
import com.example.shopease.repository.interfaces.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userRegister = MutableLiveData<Result<UserResponse>>()
    val userRegister: LiveData<Result<UserResponse>> = _userRegister

    private val _imageRes = MutableLiveData<Result<ImageUploadResponse>>()
    val imageRes: LiveData<Result<ImageUploadResponse>> = _imageRes

    fun registerUser(user: UserRegistrationRequest) {
        viewModelScope.launch {
            _userRegister.value = userRepository.register(user)
        }
    }

    fun uploadImageToServer(file: File) {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        viewModelScope.launch {
            val result = userRepository.uploadFileToServer(body)
            _imageRes.postValue(result)
        }
    }
}
