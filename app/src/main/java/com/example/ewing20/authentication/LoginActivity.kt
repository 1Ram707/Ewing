package com.example.ewing20.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.ewing20.R
import com.example.ewing20.map.MapsActivity
import com.example.ewing20.databinding.ActivityLoginBinding
import com.example.ewing20.map.bird.birdDBHelper.DBHelper

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.login_button)

        loginButton.setOnClickListener {
            login()
        }
        val registerButton: TextView = findViewById(R.id.no_account)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val email = emailEditText.text.toString().trim() // trim() to remove leading/trailing white spaces
        val password = passwordEditText.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }

        // check if the user exists in the database
        val dbHelper = DBHelper(this, null)
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${DBHelper.TABLE_USERS} WHERE ${DBHelper.COLUMN_USER_EMAIL}=?", arrayOf(email))
        if (cursor != null && cursor.moveToFirst()) {
            val storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_PASSWORD))
            if (storedPassword == password) {
                Toast.makeText(this, "You have been successfully logged in", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
        }
        cursor?.close()
        db.close()
        dbHelper.close()
    }
}