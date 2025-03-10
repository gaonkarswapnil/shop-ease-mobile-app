package com.example.shopease.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.shopease.R
import com.example.shopease.model.ResponseCategoriesItem

class CategoriesAdapter(
    private val list: List<ResponseCategoriesItem>,
    private val listener: onItemClickListener
): RecyclerView.Adapter<CategoriesAdapter.CategoryItemHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION
    interface onItemClickListener{
        fun onItemClick(category: ResponseCategoriesItem)
    }

    class CategoryItemHolder(view: View): RecyclerView.ViewHolder(view){
        val card: CardView = itemView.findViewById(R.id.cardCategories)
        val genreImg: ImageView = itemView.findViewById(R.id.img_category_item)
        val genreText: TextView = itemView.findViewById(R.id.tv_category_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categories_list, parent, false)
        return CategoryItemHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryItemHolder, position: Int) {
        val category = list[position]
        Glide
            .with(holder.genreImg.context)
            .load(category.image)
            .error(R.drawable.ic_person)
            .into(holder.genreImg)

        holder.genreText.text = category.name

        holder.card.isSelected = position == selectedPosition

        if(position == 0){
            listener.onItemClick(category)
        }

        holder.card.setOnClickListener {
            val currentPosition = holder.adapterPosition
            if (currentPosition != RecyclerView.NO_POSITION && currentPosition != selectedPosition) {
//                selectedPosition = currentPosition
//                notifyDataSetChanged()
//                listener.onItemClick(category)

                val previousPosition = selectedPosition
                selectedPosition = currentPosition

                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)

                listener.onItemClick(category)
            }
        }
    }

}