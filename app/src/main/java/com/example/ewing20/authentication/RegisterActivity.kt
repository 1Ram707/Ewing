package com.example.ewing20.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ewing20.R
import com.example.ewing20.databinding.ActivityRegisterBinding
import com.example.ewing20.map.bird.birdDBHelper.DBHelper

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText

    // create a DBHelper instance to access the database
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = DBHelper(this, null)

        usernameEditText = findViewById(R.id.username)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        confirmPasswordEditText = findViewById(R.id.confirmPassword)

        val registerButton: Button = findViewById(R.id.register_button)
        registerButton.setOnClickListener {
            registerUser()
        }
        val haveAccountTextView: TextView = findViewById(R.id.have_account)
        haveAccountTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun registerUser() {
        val username = usernameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        if (username.isEmpty()) {
            usernameEditText.error = "Username is required"
            return
        }

        if (email.isEmpty()) {
            emailEditText.error = "Email is required"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Please enter a valid email"
            return
        }

        if (password.isEmpty()) {
            passwordEditText.error = "Password is required"
            return
        }

        if (confirmPassword.isEmpty()) {
            confirmPasswordEditText.error = "Please confirm password"
            return
        }

        if (password != confirmPassword) {
            confirmPasswordEditText.error = "Passwords do not match"
            return
        }

        // Here you can add code to create a new user in your database

        // insert the new user into the database
        dbHelper.insertUser(username, email, password)

        Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
        // Navigate to login screen
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}