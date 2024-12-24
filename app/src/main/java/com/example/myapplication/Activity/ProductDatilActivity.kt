package com.example.myapplication.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.Model.Item
import com.example.myapplication.R

class ProductDatilActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        // Find the back button by its ID
        val backButton:ImageView = findViewById(R.id.backButton)

        // Set a click listener on the back button
        backButton.setOnClickListener {
            // Navigate back or close the current activity
            onBackPressed()
        }
        // Retrieve the passed product
        val product = intent.getParcelableExtra<Item>("product")

        // Set data to the views
        findViewById<TextView>(R.id.productName).text = product?.name
        findViewById<TextView>(R.id.productDescription).text = product?.description
        findViewById<TextView>(R.id.productPrice).text = "$ ${product?.price?.toString()}"

        Glide.with(this).load(product?.image).into(findViewById(R.id.productImage))
    }
}
