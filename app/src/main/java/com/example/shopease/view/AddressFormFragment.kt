package com.example.shopease.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shopease.R
import com.example.shopease.databinding.FragmentAddressFormBinding
import com.example.shopease.model.Address
import com.example.shopease.viewmodel.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressFormFragment : Fragment() {
    private lateinit var binding: FragmentAddressFormBinding
    private val addressModel: AddressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddressFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })

        binding.btnSaveAddress.setOnClickListener {
            val address = Address(
                address = binding.etAddress.text.toString(),
                landmark = binding.etLandmark.text.toString(),
                city = binding.etCity.text.toString(),
                state = binding.etState.text.toString(),
                zipCode = binding.etZipCode.text.toString().toInt(),
                country = binding.etCountry.text.toString()
            )

            addressModel.insertAddress(address)
            findNavController().navigate(R.id.action_addressFormFragment_to_addressListFragment)
        }


        binding.ivBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

}