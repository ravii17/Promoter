package com.example.organisation

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.organisation.R
import com.example.organisation.databinding.ActivityAddPaymentBinding

class AddPaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using ViewBinding
        binding = ActivityAddPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.tabCard.setOnClickListener { selectTab(binding.tabCard) }
        binding.tabUpi.setOnClickListener { selectTab(binding.tabUpi) }
        binding.tabNetBanking.setOnClickListener { selectTab(binding.tabNetBanking) }

        binding.btnSavePayment.setOnClickListener {
            val intent = android.content.Intent(this, HomeActivity::class.java)
            intent.putExtra("IS_PAYMENT_COMPLETED", true)
            intent.flags = android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP or android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }

    private fun selectTab(selectedTab: TextView) {
        val tabs = listOf(binding.tabCard, binding.tabUpi, binding.tabNetBanking)
        
        tabs.forEach { tab: TextView ->
            if (tab == selectedTab) {
                tab.setBackgroundResource(R.drawable.bg_tab_selected)
                tab.setTextColor(Color.parseColor("#1A1A1A"))
            } else {
                tab.setBackgroundResource(R.drawable.bg_tab_unselected)
                tab.setTextColor(Color.parseColor("#BDBDBD"))
            }
        }

        // Handle content visibility
        binding.cardContent.visibility = if (selectedTab == binding.tabCard) android.view.View.VISIBLE else android.view.View.GONE
        binding.upiContent.visibility = if (selectedTab == binding.tabUpi) android.view.View.VISIBLE else android.view.View.GONE
        binding.netBankingContent.visibility = if (selectedTab == binding.tabNetBanking) android.view.View.VISIBLE else android.view.View.GONE
        
        // Adjust secure note constraint if necessary (it's already below contentContainer in XML)
    }
}
