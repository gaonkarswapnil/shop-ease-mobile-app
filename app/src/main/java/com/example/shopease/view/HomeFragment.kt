package com.example.shopease.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopease.adapter.CategoriesAdapter
import com.example.shopease.databinding.FragmentHomeBinding
import com.example.shopease.model.ResponseCategoriesItem
import com.example.shopease.utils.NetworkManager
import com.example.shopease.adapter.ProductCategoryAdapter
import com.example.shopease.interfaces.OnItemClickListener
import com.example.shopease.interfaces.OnProductClick
import com.example.shopease.model.ProductByCategoryItem
import com.example.shopease.viewmodel.CategoriesViewModel
import com.example.shopease.viewmodel.WishlistViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HomeFragment : Fragment(), OnItemClickListener, OnProductClick {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var session: NetworkManager

    private val viewModel: CategoriesViewModel by viewModels()
    private val wishList: WishlistViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = NetworkManager(requireActivity())

        viewModel.fetchCategories()

        viewModel.categories.observe(viewLifecycleOwner, Observer { response ->
            response
                .onSuccess {
                    Log.d("ok_data", it[0].toString())
                    binding.rvCategory.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                    binding.rvCategory.adapter = CategoriesAdapter(it, this)

                }.onFailure {
                    Toast.makeText(requireContext(), "Unable to Load categories", Toast.LENGTH_SHORT).show()
                }
        })

        viewModel.categoryByProduct.observe(viewLifecycleOwner, Observer { response ->
            response
                .onSuccess {
                    binding.rvProductCategory.layoutManager = GridLayoutManager(requireContext(), 2)
//                    binding.rvProductCategory.layoutManager = LinearLayoutManager(requireContext())
//                    binding.rvProductCategory.setHasFixedSize(true)
//                    binding.rvProductCategory.isNestedScrollingEnabled = false
                    val adapater  = ProductCategoryAdapter(it, this)
                    binding.rvProductCategory.adapter = adapater
                }.onFailure {
                    Toast.makeText(requireContext(),"Unable to Load Products", Toast.LENGTH_SHORT).show()
                }
        })


    }

    override fun onItemClick(category: ResponseCategoriesItem) {
        viewModel.fetchProductsByCategory(category.id)//0 1
    }

    override fun onProductClick(product: ProductByCategoryItem) {
        CoroutineScope(Dispatchers.IO).launch {
            val isWishList = wishList.isProductAvailable(product.id)

            withContext(Dispatchers.Main){
                if(isWishList){
                    wishList.removeFromWishlist(product.id)
                }else{
                    wishList.addToWishList(product)
                }
            }
        }
    }
}