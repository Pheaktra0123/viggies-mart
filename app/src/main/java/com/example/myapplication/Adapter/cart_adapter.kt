package com.example.myapplication.Adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.Item
import com.example.myapplication.R

class CartAdapter(
    private val context: Context,
    private val cartItems: List<Item>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.product_name)
        val itemPrice: TextView = view.findViewById(R.id.product_price)
        val itemQuantity: TextView = view.findViewById(R.id.product_quantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_checkout, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.itemName.text = item.name
        holder.itemPrice.text = "${item.price}$/Kg"
        holder.itemQuantity.text = "Qty: ${item.quantity}"
    }

    override fun getItemCount(): Int = cartItems.size
}
