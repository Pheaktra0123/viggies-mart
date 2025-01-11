package com.example.myapplication.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
        val itemDescription: TextView = view.findViewById(R.id.product_description)
        val itemPrice: TextView = view.findViewById(R.id.product_price)
        val addToCartButton: ImageButton = view.findViewById(R.id.add_to_cart_button)

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
            addToCartButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = itemList[position]
                    CartManager.addItemToCart(item)
                    Toast.makeText(context, "${item.name} added to cart!", Toast.LENGTH_SHORT).show()
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
        holder.itemDescription.text = item.description
        holder.itemPrice.text = "${item.price}$/Kg"
        Glide.with(context).load(item.image).into(holder.itemImage)
    }

    override fun getItemCount(): Int = itemList.size
}
object CartManager {
    private val cartItems = mutableListOf<Item>()

    fun addItemToCart(item: Item) {
        val existingItem = cartItems.find { it.name == item.name }
        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            item.quantity = 1
            cartItems.add(item)
        }
    }

    fun getCartItems(): List<Item> = cartItems
}

