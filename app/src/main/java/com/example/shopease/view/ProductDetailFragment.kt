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
import com.example.shopease.model.AddToCart
import com.example.shopease.model.Category
import com.example.shopease.model.ProductByCategoryItem
import com.example.shopease.viewmodel.CartViewModel
import com.example.shopease.viewmodel.ProductsViewModel
import com.example.shopease.viewmodel.WishlistViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding
    private val product: ProductsViewModel by viewModels()
    private val wishList: WishlistViewModel by viewModels()
    private val cart: CartViewModel by viewModels()

    private var productDetail: ProductByCategoryItem? = null
    private var addToCart: AddToCart? = null

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

                    addToCart = AddToCart(
                        category = Category(
                            creationAt = it.category.creationAt,
                            id = it.category.id,
                            image = it.category.image,
                            name = it.category.name,
                            slug = it.category.slug,
                            updatedAt = it.category.updatedAt
                        ),
                        creationAt = it.creationAt,
                        description = it.description,
                        id = it.id,
                        images = it.images,
                        price = it.price,
                        slug = it.slug,
                        title = it.title,
                        updatedAt = it.updatedAt
                    )
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

        lifecycleScope.launch {
            if(cart.isProductInCart(id)){
                binding.btnAddToCart.text = "Remove From Cart"
            }else{
                binding.btnAddToCart.text = "Add To Cart"
            }
        }


        
        binding.btnAddToCart.setOnClickListener {
            lifecycleScope.launch {
                if(cart.isProductInCart(id)){
                    cart.removeFromCart(id)
                    binding.btnAddToCart.text = "Add To Cart"
                }else{
                    cart.addToCart(addToCart!!)
                    binding.btnAddToCart.text = "Remove From Cart"
                }
            }
        }
    }

}