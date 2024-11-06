package com.example.myapplication.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.ItemAdapter
import com.example.myapplication.Model.Item
import com.example.myapplication.R
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MainActivityKotlin:AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var buttonDrawer: ImageButton

    private var recyclerView: RecyclerView? = null
    private var itemAdapter: ItemAdapter? = null
    private var itemList = ArrayList<Item>()

    private lateinit var progressBar: ProgressBar

    @SuppressLint("WrongConstant")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //for slide bar
        drawerLayout = findViewById(R.id.drawerlayout)
        buttonDrawer = findViewById(R.id.btndrawer)

        buttonDrawer.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }


        //progress bar
        progressBar=findViewById(R.id.progressBar)
        Handler(Looper.getMainLooper()).postDelayed({
            showLoading(false)
        },3000)


        itemList = ArrayList()
        //for fetch data from firebase
        recyclerView = findViewById(R.id.recycle_view) as RecyclerView
        itemAdapter = ItemAdapter(this@MainActivityKotlin, itemList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = itemAdapter

        showLoading(true)
        fetchItemsFromFirebase()
    }

    private fun fetchItemsFromFirebase() {

        val database = Firebase.database
        val itemsRef = database.getReference("Product")

        itemsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Item::class.java)
                    if (item != null) {
                        itemList.add(item)
                    }
                }
                itemAdapter?.run { notifyDataSetChanged() }
                showLoading(false)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "Database error: ${error.message}")
                // Hide loading if there's an error
                showLoading(false)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            recyclerView?.visibility=View.GONE
        } else {
            progressBar.visibility = View.GONE
            recyclerView?.visibility = View.VISIBLE
        }

    }
}


