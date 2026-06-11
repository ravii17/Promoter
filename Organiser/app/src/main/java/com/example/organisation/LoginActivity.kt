package com.example.organisation

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.organisation.data.UserSession
import com.example.organisation.data.api.RetrofitClient
import com.example.organisation.data.api.SendOtpRequest
import com.example.organisation.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserSession.init(this)

        if (!UserSession.onboardingComplete) {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
            return
        }

        if (UserSession.isLoggedIn() && !UserSession.userName.isNullOrBlank()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            val recipient = binding.etRecipient.text.toString().trim()
            if (!isValidRecipient(recipient)) {
                binding.etRecipient.error = "Enter a valid phone number or email"
                return@setOnClickListener
            }
            sendOtp(recipient)
        }
    }

    private fun isValidRecipient(value: String): Boolean {
        if (value.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(value).matches()
        }
        val digits = value.filter { it.isDigit() }
        return digits.length == 10
    }

    private fun sendOtp(recipient: String) {
        showLoading(true)
        lifecycleScope.launch {
            try {
                val api = RetrofitClient.getApi(this@LoginActivity)
                val normalized = if (recipient.contains("@")) {
                    recipient.lowercase()
                } else {
                    recipient.filter { it.isDigit() }
                }

                val response = api.sendOtp(SendOtpRequest(normalized))
                if (response.success) {
                    val intent = Intent(this@LoginActivity, OtpVerificationActivity::class.java)
                    intent.putExtra(OtpVerificationActivity.EXTRA_RECIPIENT, normalized)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, response.message, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnContinue.isEnabled = !isLoading
    }
}
