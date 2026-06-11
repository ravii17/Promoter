package com.example.organisation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.example.organisation.data.UserSession
import com.example.organisation.data.api.RetrofitClient
import com.example.organisation.data.api.UpdateRoleRequest
import kotlinx.coroutines.launch

class RoleSelectionActivity : AppCompatActivity() {

    private var selectedRole: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_selection)
        UserSession.init(this)

        val cardOrganiser = findViewById<CardView>(R.id.cardOrganiser)
        val cardCrew = findViewById<CardView>(R.id.cardCrew)
        val cardBuyer = findViewById<CardView>(R.id.cardBuyer)
        val btnContinue = findViewById<Button>(R.id.btnContinue)

        val cards = mapOf(
            "organiser" to cardOrganiser,
            "crew" to cardCrew,
            "buyer" to cardBuyer
        )

        cards.forEach { (role, card) ->
            card.setOnClickListener {
                selectedRole = role
                cards.values.forEach { it.alpha = 0.6f }
                card.alpha = 1f
                btnContinue.isEnabled = true
            }
        }

        btnContinue.setOnClickListener {
            val role = selectedRole ?: return@setOnClickListener
            submitRole(role)
        }
    }

    private fun submitRole(role: String) {
        findViewById<View>(R.id.progressBar).visibility = View.VISIBLE
        findViewById<Button>(R.id.btnContinue).isEnabled = false

        lifecycleScope.launch {
            try {
                val api = RetrofitClient.getApi(this@RoleSelectionActivity)
                val response = api.updateRole(UpdateRoleRequest(role))

                UserSession.userRole = response.role
                UserSession.kycStatus = response.kyc_status

                val next = Intent(this@RoleSelectionActivity, ProfileIntroActivity::class.java)
                next.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(next)
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@RoleSelectionActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                findViewById<View>(R.id.progressBar).visibility = View.GONE
                findViewById<Button>(R.id.btnContinue).isEnabled = true
            }
        }
    }
}
