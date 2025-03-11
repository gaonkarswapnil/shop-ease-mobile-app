package com.example.shopease.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ActivityNavigatorExtras
import com.example.shopease.R
import com.example.shopease.databinding.ActivityHomeBinding
import com.example.shopease.utils.NetworkManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val session = NetworkManager(this)

        Log.d("ok_DATA","${session.getSessionToken()}")

        binding.bottomNavigation.setOnItemSelectedListener{ menu ->
            val navController = findNavController(R.id.nav_home_controller)

            when(menu.itemId){
                R.id.home ->{
                    navController.popBackStack(R.id.homeFragment, false)
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.cart -> {
                    true
                }

                R.id.wishList -> {
                    true
                }

                R.id.order -> {
                    true
                }

                R.id.profile -> {
                    true
                }

                else -> {
                    false
                }
            }
        }
    }
}