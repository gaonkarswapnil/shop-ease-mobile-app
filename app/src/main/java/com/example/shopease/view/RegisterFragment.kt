package com.example.shopease.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.shopease.R
import com.example.shopease.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URI

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    private var base64Image: String? = null

    companion object{
        private const val PICK_IMAGE_REQUEST = 1
    }

    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissions()

        binding.ivProfilePic.setOnClickListener{
            openImagePicker()
        }

        binding.btnRegister.setOnClickListener {
            // TODO: Implement registration logic here
            Log.d("LOGDATA", base64Image?:"Data not available")
        }

    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*" // Filter to show only images
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == android.app.Activity.RESULT_OK && data != null) {
            imageUri = data.data // Get the URI of the selected image
            imageUri?.let { uri ->
                // Display the selected image in an ImageView
                val imageView: ImageView = binding.ivProfilePic!!
//                imageView.setImageURI(uri)
                Glide.with(imageView.context)
                    .load(uri)
                    .circleCrop()
                    .into(imageView)
                // Optionally convert the image to Base64 for API submission
                try {
                    base64Image = convertUriToBase64(uri)
                    Toast.makeText(activity, "Base64 Image: $base64Image", Toast.LENGTH_LONG).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(activity, "Error converting image to Base64", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Convert the image URI to Base64 (for API submission if needed)
    private fun convertUriToBase64(uri: Uri): String {
        val inputStream: InputStream = activity?.contentResolver?.openInputStream(uri) ?: throw IOException("Unable to open input stream")
        val byteArrayOutputStream = ByteArrayOutputStream()

        val buffer = ByteArray(1)
        var length: Int
        while (inputStream.read(buffer).also { length = it } != -1) {
            byteArrayOutputStream.write(buffer, 0, length)
        }
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT) // Encode image to Base64 string
    }


    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker()
            } else {
                Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}