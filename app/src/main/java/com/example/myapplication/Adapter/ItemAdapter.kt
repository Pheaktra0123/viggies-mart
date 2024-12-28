package com.example.myapplication.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Activity.MainActivityKotlin
import com.example.myapplication.Activity.ProductDatilActivity
import com.example.myapplication.Model.Item
import com.example.myapplication.R
class ItemAdapter(
    private val context: Context,
    private val itemList: List<Item>
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.product_image)
        val itemName: TextView = view.findViewById(R.id.product_name)
        val itemDescription:TextView=view.findViewById(R.id.product_description)
        val itemPrice: TextView = view.findViewById(R.id.product_price)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = itemList[position]
                    val intent = Intent(context, ProductDatilActivity::class.java)
                    intent.putExtra("product", item)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemName.text = item.name
        holder.itemDescription.text=item.description
        holder.itemPrice.text = "${item.price}$/Kg"
        Glide.with(context).load(item.image).into(holder.itemImage)
    }

    override fun getItemCount(): Int = itemList.size
}

