package com.example.organisation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.organisation.data.UserSession
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingActivity : AppCompatActivity() {

    private val slides = listOf(
        OnboardingSlide(
            "Hire verified event crew instantly",
            "Connect with skilled professionals for your next event in minutes."
        ),
        OnboardingSlide(
            "Manage events end-to-end",
            "Create jobs, review applicants, and track spending from one dashboard."
        ),
        OnboardingSlide(
            "Chat and coordinate in real time",
            "Stay in sync with your crew before, during, and after the event."
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserSession.init(this)

        if (UserSession.onboardingComplete) {
            routeAfterOnboarding()
            return
        }

        setContentView(R.layout.activity_onboarding)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val btnSkip = findViewById<TextView>(R.id.btnSkip)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val tabLayout = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabLayout)

        viewPager.adapter = OnboardingAdapter(slides)

        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        btnSkip.setOnClickListener { finishOnboarding() }

        btnNext.setOnClickListener {
            if (viewPager.currentItem == slides.lastIndex) {
                finishOnboarding()
            } else {
                viewPager.currentItem += 1
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                btnNext.text = if (position == slides.lastIndex) "Get Started" else "Next"
            }
        })
    }

    private fun finishOnboarding() {
        UserSession.onboardingComplete = true
        routeAfterOnboarding()
    }

    private fun routeAfterOnboarding() {
        val target = if (UserSession.isLoggedIn()) {
            Intent(this, HomeActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }
        startActivity(target)
        finish()
    }

    data class OnboardingSlide(val title: String, val description: String)

    private class OnboardingAdapter(
        private val items: List<OnboardingSlide>
    ) : RecyclerView.Adapter<OnboardingAdapter.SlideViewHolder>() {

        class SlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvTitle: TextView = view.findViewById(R.id.tvSlideTitle)
            val tvDescription: TextView = view.findViewById(R.id.tvSlideDescription)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_onboarding_slide, parent, false)
            return SlideViewHolder(view)
        }

        override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
            val slide = items[position]
            holder.tvTitle.text = slide.title
            holder.tvDescription.text = slide.description
        }

        override fun getItemCount(): Int = items.size
    }
}
