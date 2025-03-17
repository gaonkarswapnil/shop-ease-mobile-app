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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.shopease.R
import com.example.shopease.databinding.FragmentProductDetailBinding
import com.example.shopease.model.ProductByCategoryItem
import com.example.shopease.viewmodel.ProductsViewModel
import com.example.shopease.viewmodel.WishlistViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding
    private val product: ProductsViewModel by viewModels()
    private val wishList: WishlistViewModel by viewModels()

    private var productDetail: ProductByCategoryItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })

        val id = arguments?.getInt("productId") ?: -1

        product.getSingleProduct(id)

        Log.d("DEMO_ID", id.toString())
        product.singleProduct.observe(viewLifecycleOwner, Observer { response ->
            response
                .onSuccess {
                    productDetail = it
                    binding.tvProductName.text = it.title
                    binding.tvProductDescription.text = it.description
                    binding.tvPrice.text = "$ ${it.price}"

                    Glide
                        .with(binding.ivProductImage.context)
                        .load(it.images.first())
                        .placeholder(R.drawable.images)
                        .into(binding.ivProductImage)

                    if(it.category.name.lowercase() == "clothes"){
                        binding.tvSize.visibility = View.VISIBLE
                        binding.rgSize.visibility = View.VISIBLE
                    }else{
                        binding.tvSize.visibility = View.GONE
                        binding.rgSize.visibility = View.GONE
                    }
                }
                .onFailure {
                    Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
                }
        })

        binding.ivBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        lifecycleScope.launch {
            if(wishList.isProductAvailable(id)){
                binding.ivWishlist.setImageResource(R.drawable.after_wishlist)
            }
            else{
                binding.ivWishlist.setImageResource(R.drawable.before_wishlist)
            }
        }

        binding.ivWishlist.setOnClickListener {
            lifecycleScope.launch {
                if(wishList.isProductAvailable(id)){
                    binding.ivWishlist.setImageResource(R.drawable.before_wishlist)
                    wishList.removeFromWishlist(id)
                }
                else{
                    binding.ivWishlist.setImageResource(R.drawable.after_wishlist)
                    wishList.addToWishList(productDetail!!)
                }
            }

        }
    }

}