package com.example.organisation

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.organisation.data.UserSession
import com.example.organisation.data.api.RetrofitClient
import com.example.organisation.data.api.SendOtpRequest
import com.example.organisation.data.api.VerifyOtpRequest
import kotlinx.coroutines.launch

class OtpVerificationActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_RECIPIENT = "recipient"
        private const val RESEND_SECONDS = 30
        private const val MAX_ATTEMPTS = 3
    }

    private lateinit var recipient: String
    private lateinit var otpFields: List<EditText>
    private var attempts = 0
    private var resendTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)
        UserSession.init(this)

        recipient = intent.getStringExtra(EXTRA_RECIPIENT).orEmpty()
        if (recipient.isBlank()) {
            finish()
            return
        }

        findViewById<TextView>(R.id.tvOtpSubtitle).text =
            "Enter the 4-digit code sent to $recipient"

        otpFields = listOf(
            findViewById(R.id.etOtp1),
            findViewById(R.id.etOtp2),
            findViewById(R.id.etOtp3),
            findViewById(R.id.etOtp4)
        )

        setupOtpInputs()

        findViewById<TextView>(R.id.btnVerify).setOnClickListener { verifyOtp() }
        findViewById<TextView>(R.id.btnResend).setOnClickListener { resendOtp() }

        startResendCountdown()
    }

    private fun setupOtpInputs() {
        otpFields.forEachIndexed { index, field ->
            field.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1 && index < otpFields.lastIndex) {
                        otpFields[index + 1].requestFocus()
                    }
                    if (otpCode().length == 4) {
                        verifyOtp()
                    }
                }
            })

            field.setOnKeyListener { _, keyCode, event ->
                if (event.action == android.view.KeyEvent.ACTION_DOWN &&
                    keyCode == android.view.KeyEvent.KEYCODE_DEL &&
                    field.text.isEmpty() && index > 0
                ) {
                    otpFields[index - 1].requestFocus()
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun otpCode(): String = otpFields.joinToString("") { it.text.toString().trim() }

    private fun verifyOtp() {
        val otp = otpCode()
        if (otp.length != 4) {
            Toast.makeText(this, "Enter the 4-digit OTP", Toast.LENGTH_SHORT).show()
            return
        }

        if (attempts >= MAX_ATTEMPTS) {
            Toast.makeText(this, "Too many attempts. Please resend OTP.", Toast.LENGTH_LONG).show()
            return
        }

        attempts += 1
        setLoading(true)

        lifecycleScope.launch {
            try {
                val api = RetrofitClient.getApi(this@OtpVerificationActivity)
                val response = api.verifyOtp(VerifyOtpRequest(recipient, otp))

                UserSession.token = response.token
                UserSession.userId = response.user_id
                UserSession.userName = response.name
                UserSession.userEmail = response.email
                UserSession.userPhone = response.phone ?: recipient.takeIf { !it.contains("@") }
                UserSession.userCity = response.city
                UserSession.userRole = response.role
                UserSession.kycStatus = response.kyc_status
                UserSession.profilePhotoUrl = response.profile_photo_url

                val next = when {
                    response.is_new_user || response.role == "pending_role_selection" || response.role == "pending" ->
                        Intent(this@OtpVerificationActivity, RoleSelectionActivity::class.java)
                    !response.profile_complete ->
                        Intent(this@OtpVerificationActivity, ProfileIntroActivity::class.java)
                    response.kyc_status == "pending" ->
                        Intent(this@OtpVerificationActivity, KYCActivity::class.java)
                    else ->
                        Intent(this@OtpVerificationActivity, HomeActivity::class.java)
                }

                next.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(next)
                finish()
            } catch (_: Exception) {
                val container = findViewById<View>(R.id.otpContainer)
                container.animate()
                    .translationX(20f)
                    .setDuration(50L)
                    .withEndAction {
                        container.animate()
                            .translationX(-20f)
                            .setDuration(100L)
                            .withEndAction {
                                container.animate()
                                    .translationX(0f)
                                    .setDuration(50L)
                                    .start()
                            }
                            .start()
                    }
                    .start()
                Toast.makeText(this@OtpVerificationActivity, "Invalid OTP. Try again.", Toast.LENGTH_LONG).show()
                otpFields.forEach { it.text.clear() }
                otpFields.first().requestFocus()
            } finally {
                setLoading(false)
            }
        }
    }

    private fun resendOtp() {
        setLoading(true)
        lifecycleScope.launch {
            try {
                val api = RetrofitClient.getApi(this@OtpVerificationActivity)
                api.sendOtp(SendOtpRequest(recipient))
                attempts = 0
                Toast.makeText(this@OtpVerificationActivity, "OTP resent", Toast.LENGTH_SHORT).show()
                startResendCountdown()
            } catch (e: Exception) {
                Toast.makeText(this@OtpVerificationActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                setLoading(false)
            }
        }
    }

    private fun startResendCountdown() {
        val btnResend = findViewById<TextView>(R.id.btnResend)
        btnResend.isEnabled = false
        resendTimer?.cancel()
        resendTimer = object : CountDownTimer(RESEND_SECONDS * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                btnResend.text = "Resend in ${seconds}s"
            }

            override fun onFinish() {
                btnResend.isEnabled = true
                btnResend.text = "Resend OTP"
            }
        }.start()
    }

    private fun setLoading(loading: Boolean) {
        findViewById<View>(R.id.progressBar).visibility = if (loading) View.VISIBLE else View.GONE
        findViewById<View>(R.id.btnVerify).isEnabled = !loading
        findViewById<View>(R.id.btnResend).isEnabled = !loading && (resendTimer == null || (findViewById<TextView>(R.id.btnResend).text == "Resend OTP"))
        otpFields.forEach { it.isEnabled = !loading }
    }

    override fun onDestroy() {
        resendTimer?.cancel()
        super.onDestroy()
    }
}
