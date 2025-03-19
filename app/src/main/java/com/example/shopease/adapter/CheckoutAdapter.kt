package com.example.shopease.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.appendPlaceholders
import com.bumptech.glide.Glide
import com.example.shopease.R
import com.example.shopease.model.AddToCart

class CheckoutAdapter(
    private val list: List<AddToCart>
): RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder>() {

    class CheckoutViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.iv_checkout_product_image)
        val title = view.findViewById<TextView>(R.id.tv_cart_product_title)
        val quantity = view.findViewById<TextView>(R.id.tv_checkout_quantity)
        val price = view.findViewById<TextView>(R.id.tv_checkout_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.checkout_product, parent, false)
        return CheckoutViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
        val data = list[position]

        with(holder){
            Glide
                .with(image.context)
                .load(data.images.first())
                .placeholder(R.drawable.images)
                .into(image)

            price.text = data.price.toString()
            title.text = data.title
            quantity.text = data.quantity.toString()
        }
    }

    fun calculateTotalPrice(): Long {
        return list.sumOf { it.price * it.quantity}
    }
}