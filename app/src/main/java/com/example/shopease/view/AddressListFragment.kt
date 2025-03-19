package com.example.shopease.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopease.R
import com.example.shopease.adapter.AddressAdapter
import com.example.shopease.databinding.FragmentAddressListBinding
import com.example.shopease.interfaces.ForAddress
import com.example.shopease.interfaces.ForProductId
import com.example.shopease.viewmodel.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressListFragment : Fragment(), ForProductId, ForAddress {
    private lateinit var binding: FragmentAddressListBinding
    private val addressModel: AddressViewModel by viewModels()
    private var addressId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddressListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

        addressModel.getAddress()
        addressModel.address.observe(viewLifecycleOwner, Observer { response ->
            if (response.size > 0) {
                binding.rvAddressRecyclerView.adapter = AddressAdapter(response, this, this)
                binding.rvAddressRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            } else {
                findNavController().navigate(R.id.action_addressListFragment_to_addressFormFragment)
            }
        })

        binding.ivBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivBtnAdd.setOnClickListener {

            findNavController().navigate(R.id.action_addressListFragment_to_addressFormFragment)
        }

        binding.btnCheckout.setOnClickListener {
            if (addressId == -1) {
                Toast.makeText(requireActivity(), "Select one Address", Toast.LENGTH_SHORT).show()
            } else {
//                Log.d("DEMO_ID", "$addressId")
                val bundle = Bundle()
                bundle.putInt("AddressId", addressId)
                findNavController().navigate(R.id.action_addressListFragment_to_checkoutFragment, bundle)
            }
        }
    }

    override fun onItemClick(productId: Int) {
        addressModel.removeAddress(productId)
        addressModel.getAddress()
    }

    override fun addressId(id: Int) {
        addressId = id
    }
}