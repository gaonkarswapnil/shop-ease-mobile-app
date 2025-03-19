package com.example.shopease.view

import android.content.Context
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
import com.example.shopease.BuildConfig
import com.example.shopease.R
import com.example.shopease.adapter.CheckoutAdapter
import com.example.shopease.databinding.FragmentCheckoutBinding
import com.example.shopease.viewmodel.AddressViewModel
import com.example.shopease.viewmodel.CartViewModel
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class CheckoutFragment : Fragment(), PaymentResultListener {
    private lateinit var binding: FragmentCheckoutBinding
    private val cart: CartViewModel by viewModels()
    private val address: AddressViewModel by viewModels()
    private var price: Long? = null

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
            price = adapter.calculateTotalPrice()
            binding.tvTotalPriceInCheckout.text = "$ ${price}"
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

        binding.btnPayment.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
//                val str = binding.tvTotalPriceInCheckout.text.toString()
                val amount = (price!!.toFloat() * 100).toInt() * 80
                val checkout = Checkout()
                checkout.setKeyID(BuildConfig.RAZOR_PAY)
                checkout.setImage(R.drawable.app_icon)
                val paymentData = JSONObject()

                try{
                    paymentData.apply{
                        put("name",getString(R.string.app_name))
                        put("description","Test Description")
                        put("theme.color", "#3399cc")
                        put("currency", "INR")
                        put("amount",amount)
                        put("prefill.contact","9999999999")
                        put("prefill.email", "oliver@gmail.com");
                        checkout.open(requireActivity(), paymentData)
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        })
    }

    override fun onPaymentSuccess(s: String?) {
        Toast.makeText(requireContext(), "Thankyou for shopping with us...", Toast.LENGTH_SHORT).show();
        cart.removeAllItems()
    }

    override fun onPaymentError(i: Int, s: String?) {
        Toast.makeText(requireContext(), "Payment Failed due to error : $s", Toast.LENGTH_SHORT).show();
    }


}