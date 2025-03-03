package com.example.shopease.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shopease.R
import com.example.shopease.databinding.ActivityUserAuthenticationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserAuthenticationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentLoad = intent.getStringExtra("FRAGMENT_TO_LOAD")

        if(fragmentLoad == "Register"){
            supportFragmentManager.beginTransaction()
               .replace(R.id.fragment_container, RegisterFragment())
               .commit()
        }

    }
}