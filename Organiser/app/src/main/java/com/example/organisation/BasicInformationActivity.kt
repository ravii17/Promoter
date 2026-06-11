package com.example.organisation

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.organisation.data.UserSession

class BasicInformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_information)
        UserSession.init(this)

        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etCity = findViewById<EditText>(R.id.etCity)

        etPhone.setText(UserSession.userPhone.orEmpty())
        etPhone.isEnabled = false
        etEmail.setText(UserSession.userEmail.orEmpty())

        findViewById<TextView>(R.id.btnBack).setOnClickListener { finish() }

        findViewById<Button>(R.id.btnNext).setOnClickListener {
            val name = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val city = etCity.text.toString().trim()

            if (name.length < 2) {
                etFullName.error = "Enter your full name"
                return@setOnClickListener
            }

            if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Enter a valid email"
                return@setOnClickListener
            }

            if (city.length < 2) {
                etCity.error = "Enter your city"
                return@setOnClickListener
            }

            UserSession.userName = name
            UserSession.userEmail = email
            UserSession.userCity = city

            startActivity(Intent(this, PhotoUploadActivity::class.java))
        }
    }
}
