package com.example.shopease.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopease.R
import com.example.shopease.adapter.ProductCategoryAdapter
import com.example.shopease.databinding.FragmentWishlistBinding
import com.example.shopease.interfaces.OnProductClick
import com.example.shopease.model.ProductByCategoryItem
import com.example.shopease.viewmodel.WishlistViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class WishlistFragment : Fragment(), OnProductClick {
    private lateinit var binding: FragmentWishlistBinding
    private val wishlist: WishlistViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wishlist.getProducts()

        wishlist.wishlistProduct.observe(viewLifecycleOwner, Observer { data ->
            binding.rvWishlist.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.rvWishlist.adapter = ProductCategoryAdapter(data, this)
        })

    }

    override fun onProductClick(product: ProductByCategoryItem) {
        CoroutineScope(Dispatchers.IO).launch {
            wishlist.removeFromWishlist(product.id)
            wishlist.getProducts()
        }
    }

}