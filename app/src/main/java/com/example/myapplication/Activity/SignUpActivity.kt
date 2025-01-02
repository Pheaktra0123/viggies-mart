package com.example.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Activity.MainActivityKotlin
import com.example.myapplication.R
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonSignUp: Button
    private lateinit var inputName: EditText
    private lateinit var inputEmailPhone: EditText
    private lateinit var inputPassword: EditText
    private lateinit var inputConfirmPassword: EditText
    private lateinit var checkboxPolicy: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Bind views
        progressBar = findViewById(R.id.progressBar)
        buttonSignUp = findViewById(R.id.button_signup)
        inputName = findViewById(R.id.input_name)
        inputEmailPhone = findViewById(R.id.input_email_phone)
        inputPassword = findViewById(R.id.input_password)
        inputConfirmPassword = findViewById(R.id.input_confirm_password)
        checkboxPolicy = findViewById(R.id.checkbox_policy)

        buttonSignUp.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        val name = inputName.text.toString().trim()
        val email = inputEmailPhone.text.toString().trim()
        val password = inputPassword.text.toString().trim()
        val confirmPassword = inputConfirmPassword.text.toString().trim()

        // Validation checks
        if (name.isEmpty()) {
            inputName.error = "Name is required"
            inputName.requestFocus()
            return
        }

        if (email.isEmpty()) {
            inputEmailPhone.error = "Email is required"
            inputEmailPhone.requestFocus()
            return
        }

        if (password.isEmpty() || password.length < 6) {
            inputPassword.error = "Password must be at least 6 characters"
            inputPassword.requestFocus()
            return
        }

        if (password != confirmPassword) {
            inputConfirmPassword.error = "Passwords do not match"
            inputConfirmPassword.requestFocus()
            return
        }

        if (!checkboxPolicy.isChecked) {
            Toast.makeText(this, "You must accept the terms and policy", Toast.LENGTH_SHORT).show()
            return
        }

        // Show progress bar
        progressBar.visibility = View.VISIBLE

        // Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()

                    // Pass the name to MainActivityKotlin
                    val intent = Intent(this, MainActivityKotlin::class.java)
                    intent.putExtra("USER_NAME", name)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish() // Close the current activity
                } else {
                    val error = task.exception?.message ?: "Sign-up failed."
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                    if (task.exception is FirebaseNetworkException) {
                        Toast.makeText(this, "Check your network connection and try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

}

