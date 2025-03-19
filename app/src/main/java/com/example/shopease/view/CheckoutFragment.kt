package com.example.shopease.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopease.adapter.CheckoutAdapter
import com.example.shopease.databinding.FragmentCheckoutBinding
import com.example.shopease.viewmodel.AddressViewModel
import com.example.shopease.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutBinding
    private val cart: CartViewModel by viewModels()
    private val address: AddressViewModel by viewModels()

    private var id: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        id = arguments?.getInt("AddressId")
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
            val adapter = CheckoutAdapter(response)
            binding.rcCheckoutPage.layoutManager = LinearLayoutManager(requireContext())
            binding.rcCheckoutPage.adapter = adapter

            binding.tvTotalPriceInCheckout.text = "$ ${adapter.calculateTotalPrice()}"
        })

        address.getAddressFromId(id?:0)
        Log.d("Demo_checkout", id.toString())
        address.singleAddress.observe(viewLifecycleOwner, Observer { response ->
            Log.d("Demo_checkout", response.toString())
            binding.tvAddressDuringCheckout.text = "${response.address} \n ${response.landmark} \n ${response.city} - ${response.zipCode} \n ${response.state} \n ${response.country}"
        })

        binding.ivBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}