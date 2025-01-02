package com.example.myapplication.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var forgotPasswordText: TextView
    private lateinit var signUpText: TextView
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        FirebaseApp.initializeApp(this)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize views
        emailEditText = findViewById(R.id.input_email_phone)
        passwordEditText = findViewById(R.id.input_password)
        signInButton = findViewById(R.id.sign_in_button)
        forgotPasswordText = findViewById(R.id.forgot_password)
        signUpText = findViewById(R.id.sign_up_text)
        progressDialog = ProgressDialog(this)

        // Set up click listeners
        signInButton.setOnClickListener { signIn() }
        forgotPasswordText.setOnClickListener { sendPasswordReset() }
        signUpText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun signIn() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (!validateInputs(email, password)) return
        progressDialog.setMessage("Signing In...")
        progressDialog.show()
        println("passed");
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progressDialog.dismiss()
                if (task.isSuccessful) {
                    println("success")
                    startActivity(Intent(this, MainActivityKotlin::class.java))
                    finish()
                } else {
                    Toast.makeText(this, task.exception?.message ?: "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun sendPasswordReset() {
        val email = emailEditText.text.toString().trim()
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show()
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to send reset email: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Enter a valid email"
            return false
        }
        if (password.isEmpty() || password.length < 6) {
            passwordEditText.error = "Password must be at least 6 characters"
            return false
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivityKotlin::class.java))
            finish()
        }
    }
}
