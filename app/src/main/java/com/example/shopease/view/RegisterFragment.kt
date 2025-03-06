package com.example.shopease.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.shopease.R
import com.example.shopease.databinding.FragmentRegisterBinding
import com.example.shopease.model.LoginRequest
import com.example.shopease.model.UserRegistrationRequest
import com.example.shopease.utils.NetworkManager
import com.example.shopease.viewmodel.LoginViewModel
import com.example.shopease.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var session: NetworkManager
    private val userViewModel: UserViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    private var imageUri: Uri? = null

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val PERMISSION_REQUEST_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissions()

        binding.ivProfilePic.setOnClickListener {
            openImagePicker()
        }

        binding.btnRegister.setOnClickListener {
            registerUser()
        }

        binding.tvSignUp.setOnClickListener {

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .commit()
        }

        // Observe Image Upload Response
        userViewModel.imageRes.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess { imageResponse ->
                val imageUrl = imageResponse.location
                registerUserWithImage(imageUrl)
            }.onFailure {
                Toast.makeText(requireContext(), "Image Upload Failed", Toast.LENGTH_SHORT).show()
            }
        })

        // Observe User Registration Response
        userViewModel.userRegister.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess {
                Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()
                session = NetworkManager(requireContext())
                val request = LoginRequest(
                    it.email,
                    it.password
                )
                login(request)
            }.onFailure {
                Toast.makeText(requireContext(), "Registration Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun login(request: LoginRequest) {
        loginViewModel.login(request)

        loginViewModel.loginResponse.observe(viewLifecycleOwner, Observer { response ->
            response.onSuccess {
                session.addSessionToken(it.accessToken, it.refreshToken)
                Intent(requireContext(), HomeActivity::class.java).also {
                    startActivity(it)
                    requireActivity().finish()
                }
            }.onFailure {
                Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registerUser() {
        if (imageUri != null) {
            uploadImageToServer(imageUri!!)
        } else {
            registerUserWithImage(null)
        }
    }

    private fun registerUserWithImage(imageUrl: String?) {
        val userRegistrationRequest = UserRegistrationRequest(
            name = binding.etName.text.toString(),
            email = binding.etEmail.text.toString(),
            password = binding.etPassword.text.toString(),
            avatar = imageUrl ?: ""
        )

        userViewModel.registerUser(userRegistrationRequest)
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            imageUri?.let { uri ->
                Glide.with(binding.ivProfilePic.context).load(uri).circleCrop().into(binding.ivProfilePic)
            }
        }
    }

    private fun uploadImageToServer(uri: Uri) {
        val file = getFileFromUri(uri)
        if (file == null) {
            Log.e("Upload", "Failed to get file from URI")
            return
        }
        userViewModel.uploadImageToServer(file)
    }


    private fun getFileFromUri(uri: Uri): File? {
        val inputStream = requireContext().contentResolver.openInputStream(uri) ?: return null
        val tempFile = File.createTempFile("upload", ".jpg", requireContext().cacheDir)
        tempFile.outputStream().use { output -> inputStream.copyTo(output) }
        return tempFile
    }



    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
            }
        }
    }
}
