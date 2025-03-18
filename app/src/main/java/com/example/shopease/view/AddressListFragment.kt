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
import com.example.shopease.R
import com.example.shopease.adapter.AddressAdapter
import com.example.shopease.databinding.FragmentAddressListBinding
import com.example.shopease.interfaces.ForProductId
import com.example.shopease.viewmodel.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressListFragment : Fragment(), ForProductId {
    private lateinit var binding: FragmentAddressListBinding
    private val addressModel: AddressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddressListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })

        addressModel.getAddress()
        addressModel.address.observe(viewLifecycleOwner, Observer { response ->
            if(response.size > 0){
                binding.rvAddressRecyclerView.adapter = AddressAdapter(response, this)
                binding.rvAddressRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            }
            else{
                findNavController().navigate(R.id.action_addressListFragment_to_addressFormFragment)
            }
        })

        binding.ivBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivBtnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_addressListFragment_to_addressFormFragment)
        }
    }

    override fun onItemClick(productId: Int) {
        addressModel.removeAddress(productId)
        addressModel.getAddress()
    }
}