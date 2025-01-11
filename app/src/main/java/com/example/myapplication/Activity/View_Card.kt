package com.example.myapplication.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class View_Card:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_card)
        findViewById<View>(R.id.toolbar_icon).setOnClickListener {
            finish() // Close the activity and return to MainActivity
        }
    }
}