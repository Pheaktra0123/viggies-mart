package com.example.myapplication.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
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
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log


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
        // Retrieve the user's name from the Intent
        val userName = intent.getStringExtra("USER_NAME")

        // Display the name in the TextView
        val getNameTextView = findViewById<TextView>(R.id.getName)
        getNameTextView.text = userName ?: "User"
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

        // Replace the binding.navView with direct findViewById
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout -> {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun fetchItemsFromFirebase() {

        val database = Firebase.database
        val itemsRef = database.getReference("Product")
        itemsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                for (itemSnapshot in snapshot.children) {
                    Log.d("FirebaseData", "Snapshot: ${itemSnapshot.value}")
                    val item = itemSnapshot.getValue(Item::class.java)
                    if (item != null) {
                        itemList.add(item)
                    }
                }
                itemAdapter?.notifyDataSetChanged()
                showLoading(false)
            }


            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "Database error: ${error.message}")
                // Hide loading if there's an error
                showLoading(false)
            }
        })
    }    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            recyclerView?.visibility=View.GONE
        } else {
            progressBar.visibility = View.GONE
            recyclerView?.visibility = View.VISIBLE
        }

    }

}