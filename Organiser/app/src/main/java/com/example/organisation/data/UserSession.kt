package com.example.organisation.data

import android.content.Context
import android.content.SharedPreferences

object UserSession {
    private const val PREFS = "UserPrefs"
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        if (!::prefs.isInitialized) {
            prefs = context.applicationContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        }
    }

    var token: String?
        get() = prefs.getString("token", null)
        set(value) = prefs.edit().putString("token", value).apply()

    var userId: String?
        get() = prefs.getString("userId", null)
        set(value) = prefs.edit().putString("userId", value).apply()

    var userName: String?
        get() = prefs.getString("userName", null)
        set(value) = prefs.edit().putString("userName", value).apply()

    var userEmail: String?
        get() = prefs.getString("userEmail", null)
        set(value) = prefs.edit().putString("userEmail", value).apply()

    var userPhone: String?
        get() = prefs.getString("userPhone", null)
        set(value) = prefs.edit().putString("userPhone", value).apply()

    var userCity: String?
        get() = prefs.getString("userCity", null)
        set(value) = prefs.edit().putString("userCity", value).apply()

    var userRole: String?
        get() = prefs.getString("userRole", null)
        set(value) = prefs.edit().putString("userRole", value).apply()

    var kycStatus: String?
        get() = prefs.getString("kycStatus", null)
        set(value) = prefs.edit().putString("kycStatus", value).apply()

    var profilePhotoUrl: String?
        get() = prefs.getString("profilePhotoUrl", null)
        set(value) = prefs.edit().putString("profilePhotoUrl", value).apply()

    var onboardingComplete: Boolean
        get() = prefs.getBoolean("onboardingComplete", false)
        set(value) = prefs.edit().putBoolean("onboardingComplete", value).apply()

    fun isLoggedIn(): Boolean = !token.isNullOrBlank()

    fun clear() {
        prefs.edit().clear().apply()
    }
}
