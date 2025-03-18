package com.example.shopease.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopease.R
import com.example.shopease.interfaces.CartPriceUpdateListener
import com.example.shopease.interfaces.CartUpdate
import com.example.shopease.interfaces.ForProductId
import com.example.shopease.model.AddToCart
import com.example.shopease.model.Category

class CartAdapter(
    private val list: MutableList<AddToCart>,
    private val listener: CartPriceUpdateListener,
    private val onClick: ForProductId,
    private val update: CartUpdate
): RecyclerView.Adapter<CartAdapter.CartItemHolder>() {

    class CartItemHolder(view: View): RecyclerView.ViewHolder(view){
        val cartImage = view.findViewById<ImageView>(R.id.iv_cart_product_image)
        val cartProductTitle = view.findViewById<TextView>(R.id.tv_cart_product_title)
        val cartProductCategory = view.findViewById<TextView>(R.id.tv_cart_product_category)
        val cartProductPrice = view.findViewById<TextView>(R.id.tv_cart_product_price)

        val add = view.findViewById<ImageButton>(R.id.btn_add)
        val remove = view.findViewById<ImageButton>(R.id.btn_remove)
        val item = view.findViewById<TextView>(R.id.tv_no_of_items)

        val clear = view.findViewById<ImageButton>(R.id.ib_cart_clear)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_items, parent, false)
        return CartItemHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CartItemHolder, position: Int) {
        val data = list[position]

        Glide
            .with(holder.cartImage.context)
            .load(data.images.first())
            .placeholder(R.drawable.images)
            .into(holder.cartImage)

        holder.cartProductTitle.text = data.title
        holder.cartProductCategory.text = data.category.name
        holder.cartProductPrice.text = "$ ${data.price.toString()}"

        holder.item.text = data.quantity.toString()
        holder.add.setOnClickListener {
            data.quantity++
            cart(holder, data, position)

        }

        holder.remove.setOnClickListener {
            if (data.quantity > 1) {
                data.quantity--
                cart(holder, data, position)
//                holder.item.text = data.quantity.toString()
//                holder.cartProductPrice.text = "$ ${data.price * data.quantity}"
//                listener.onTotalPriceUpdated(calculateTotalPrice())
            }
        }

        holder.clear.setOnClickListener {
            onClick.onItemClick(data.id)
        }
    }

    private fun cart(holder: CartAdapter.CartItemHolder, data: AddToCart, position: Int) {
        holder.item.text = data.quantity.toString()
//        val finalPrice = data.price * data.quantity
//        holder.cartProductPrice.text = "$ $finalPrice"
        listener.onTotalPriceUpdated(calculateTotalPrice())

        update.onClick(data)
    }

    fun calculateTotalPrice(): Long {
        return list.sumOf { it.price * it.quantity}
    }


}