package com.example.shopease.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.shopease.R
import com.example.shopease.databinding.ActivitySplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var splashScreen: SplashScreen
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)

        splashScreen = installSplashScreen()

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val view = splashScreenView.view

            view.animate()
                .alpha(0f)
                .setDuration(3000)
                .withEndAction {
                    splashScreenView.remove()
                    navigateToNextScreen()
                }
                .start()
        }
    }

    private fun navigateToNextScreen() {
//        val accessToken = PreferenceManager.getAccessToken(this)
//        val isLoggedIn = !accessToken.isNullOrBlank()

        val isLoggedIn = false
        val nextActivity = if (isLoggedIn) {
            Toast.makeText(this, "Login Screen ", Toast.LENGTH_SHORT).show()
            Log.d(getString(R.string.tag), "Login Screen")
        } else {
            Toast.makeText(this, "Is Logged ", Toast.LENGTH_SHORT).show()
            Log.d(getString(R.string.tag), "Is Logged")

            setContentView(binding.root)

//            Navigate to Register Page
            binding.btnGetStarted.setOnClickListener {
                Intent(this, UserAuthenticationActivity::class.java).also {
                    it.putExtra("FRAGMENT_TO_LOAD", "Register")
                    startActivity(it)
                }
            }

            binding.tvSignIn.setOnClickListener {
                Intent(this, UserAuthenticationActivity::class.java).also {
                    it.putExtra("FRAGMENT_TO_LOAD", "Login")
                    startActivity(it)
                }
            }

        }

//        finish()
    }
}