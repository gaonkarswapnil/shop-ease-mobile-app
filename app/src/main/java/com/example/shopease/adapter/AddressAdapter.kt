package com.example.shopease.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopease.R
import com.example.shopease.interfaces.ForProductId
import com.example.shopease.model.Address

class AddressAdapter(
    private val list: List<Address>,
    private val onClick: ForProductId
): RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {
    class AddressViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val addressText = itemView.findViewById<RadioButton>(R.id.radioButton)
        val clear = itemView.findViewById<ImageButton>(R.id.btnClear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.address_layout, parent, false)
        return AddressViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.addressText.text = list[position].address

        holder.clear.setOnClickListener {
            onClick.onItemClick(list[position].id)
        }
    }


}