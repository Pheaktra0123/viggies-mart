package com.example.myapplication.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth

class EmailSignUpActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        firebaseAuth = FirebaseAuth.getInstance()

        val signupButton = findViewById<Button>(R.id.button_signup)
        signupButton.setOnClickListener {
            performSignUp()
        }
    }

    private fun performSignUp() {
        val emailInput = findViewById<EditText>(R.id.input_email_phone)
        val passwordInput = findViewById<EditText>(R.id.input_password)
        val confirmPasswordInput = findViewById<EditText>(R.id.input_confirm_password)
        val policyCheckbox = findViewById<CheckBox>(R.id.checkbox_policy)

    }
}