package com.example.myapplication.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Activity.MainActivityKotlin
import com.example.myapplication.Model.Item
import com.example.myapplication.R

class ItemAdapter(itemList1: MainActivityKotlin, private val itemList: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.product_name)
        val price: TextView = itemView.findViewById(R.id.product_price)
        val description: TextView = itemView.findViewById(R.id.product_description)
        val imageView: ImageView = itemView.findViewById(R.id.product_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ItemViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.name.text = item.name
        holder.price.text = item.price.toString()+"$/KG" // Convert Long to String
        holder.description.text = item.description
        Glide.with(holder.imageView.context)
            .load(item.image)
            .into(holder.imageView)
    }
    override fun getItemCount(): Int {
        return itemList.size;
    }
}
