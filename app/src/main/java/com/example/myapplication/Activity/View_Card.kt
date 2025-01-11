package com.example.myapplication.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.CartManager
import com.example.myapplication.Adapter.ItemAdapter
import com.example.myapplication.Model.Item
import com.example.myapplication.R

class View_Card : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: ItemAdapter
    private lateinit var cartItems: List<Item>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_card)

        // Initialize cart items
        cartItems = CartManager.getCartItems() ?: emptyList()

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recycle_view)
        cartAdapter = ItemAdapter(this, cartItems)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cartAdapter

        // Toolbar icon click listener
        findViewById<View>(R.id.toolbar_icon).setOnClickListener {
            finish() // Close the activity and return to MainActivity
        }
    }
}
