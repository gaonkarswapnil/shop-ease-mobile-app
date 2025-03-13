package com.example.shopease.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.shopease.R
import com.example.shopease.databinding.ActivityHomeBinding
import com.example.shopease.utils.NetworkManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val session = NetworkManager(this)

        Log.d("ok_DATA","${session.getSessionToken()}")

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_home_controller) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.productDetailFragment -> binding.bottomNavigation.visibility = View.GONE
                else -> binding.bottomNavigation.visibility = View.VISIBLE
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener{ menu ->
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
                    navController.popBackStack(R.id.wishlistFragment, false)
                    navController.navigate(R.id.wishlistFragment)
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