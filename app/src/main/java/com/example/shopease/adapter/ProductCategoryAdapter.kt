package com.example.shopease.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopease.R
import com.example.shopease.interfaces.OnProductClick
import com.example.shopease.model.ProductByCategoryItem

class ProductCategoryAdapter(
    private val list: List<ProductByCategoryItem>,
    private val onClick: OnProductClick
): RecyclerView.Adapter<ProductCategoryAdapter.ProductCategoryViewHolder>() {

    class ProductCategoryViewHolder(item: View): RecyclerView.ViewHolder(item){
        val image: ImageView = item.findViewById(R.id.iv_product_image)
        val title: TextView = item.findViewById(R.id.tv_product_name)
        val price: TextView = item.findViewById(R.id.tv_product_price)

        val icon: ImageView = item.findViewById(R.id.wishListBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.products_by_category, parent, false)
        return ProductCategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductCategoryViewHolder, position: Int) {
        val data = list[position]

        Glide
            .with(holder.image.context)
            .load(data.images[0])
            .placeholder(R.drawable.images)
            .into(holder.image)

        holder.title.text = data.title
        holder.price.text = "$ ${data.price}"

        holder.icon.setOnClickListener {
            onClick.onProductClick(data)
        }
    }


}