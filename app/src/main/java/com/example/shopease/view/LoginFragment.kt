package com.example.shopease.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.shopease.R
import com.example.shopease.databinding.FragmentLoginBinding
import com.example.shopease.model.LoginRequest
import com.example.shopease.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnLogin.setOnClickListener {
            val request = LoginRequest(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
            login(request)
        }

        binding.tvSignUp.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RegisterFragment())
                .commit()

        }
    }

    private fun login(request: LoginRequest) {
        loginViewModel.login(request)

        loginViewModel.loginResponse.observe(viewLifecycleOwner, Observer { response ->
            response.onSuccess {
                Intent(requireContext(), HomeActivity::class.java).also {
                    startActivity(it)
                    requireActivity().finish()
                }
            }.onFailure {
                Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

}