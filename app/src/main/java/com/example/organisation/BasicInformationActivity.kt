package com.example.organisation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

import android.widget.EditText
import android.widget.Toast

class BasicInformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_information)

        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etCity = findViewById<EditText>(R.id.etCity)
        val etBio = findViewById<EditText>(R.id.etBio)

        val btnBack = findViewById<TextView>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener {
            val name = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val city = etCity.text.toString().trim()
            val bio = etBio.text.toString().trim()

            // Phone number validation (e.g., exactly 10 digits)
            if (phone.length != 10 || !phone.all { it.isDigit() }) {
                etPhone.error = "Please enter a valid 10-digit mobile number"
                Toast.makeText(this, "Invalid mobile number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (name.isEmpty()) {
                etFullName.error = "Name is required"
                return@setOnClickListener
            }

            val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val editor = prefs.edit()
            
            editor.putString("userName", name)
            editor.putString("userEmail", email)
            editor.putString("userPhone", phone)
            editor.putString("userCity", city)
            editor.putString("userBio", bio)
            
            editor.apply()

            val intent = Intent(this, PhotoUploadActivity::class.java)
            startActivity(intent)
        }
    }
}