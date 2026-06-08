package com.example.eventhostmodule.utils

import android.content.Context
import com.example.eventhostmodule.data.local.SharedPrefManager

object PrefManager {

    private const val PREF_NAME = "app"
    private const val KEY_KYC_DONE = "kyc_done_"

    fun setKycDone(context: Context, value: Boolean) {
        val user = SharedPrefManager.getInstance(context).getUser()
        val suffix = user?.id?.toString() ?: "guest"
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean("$KEY_KYC_DONE$suffix", value).apply()
    }

    fun isKycDone(context: Context): Boolean {
        val user = SharedPrefManager.getInstance(context).getUser()
        val suffix = user?.id?.toString() ?: "guest"
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean("$KEY_KYC_DONE$suffix", false)
    }
}