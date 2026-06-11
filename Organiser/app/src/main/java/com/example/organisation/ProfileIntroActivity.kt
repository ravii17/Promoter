package com.example.organisation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.organisation.data.UserSession

class ProfileIntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_intro)
        UserSession.init(this)

        findViewById<Button>(R.id.btnLetsStart).setOnClickListener {
            startActivity(Intent(this, BasicInformationActivity::class.java))
            finish()
        }
    }
}
