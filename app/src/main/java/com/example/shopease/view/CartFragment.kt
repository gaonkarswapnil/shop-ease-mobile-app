package com.example.shopease.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopease.adapter.CartAdapter
import com.example.shopease.databinding.FragmentCartBinding
import com.example.shopease.interfaces.CartPriceUpdateListener
import com.example.shopease.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(), CartPriceUpdateListener {

    private lateinit var binding: FragmentCartBinding
    private val cart: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })

        cart.getAllItems()

        cart.addToCart.observe(viewLifecycleOwner, Observer { response ->
            binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
            val adapter = CartAdapter(response, this)
            binding.rvCart.adapter = adapter

            onTotalPriceUpdated(adapter.calculateTotalPrice())
        })



        binding.ivBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onTotalPriceUpdated(totalPrice: Long) {
        binding.tvTotalPrice.text = "Total: $ $totalPrice"
    }
}