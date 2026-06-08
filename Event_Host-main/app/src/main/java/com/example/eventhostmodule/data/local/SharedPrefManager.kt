package com.example.eventhostmodule.data.local

import android.content.Context
import com.example.eventhostmodule.data.model.Role
import com.example.eventhostmodule.data.model.User
import org.json.JSONObject

/**
 * Simple SharedPreferences manager for MVP state (logged-in user, role, guest flag).
 * This is a lightweight replacement for UserDefaults (iOS) while we iterate.
 */
class SharedPrefManager private constructor(private val context: Context) {

    private val prefs = context.getSharedPreferences("promotr_prefs", Context.MODE_PRIVATE)

    private fun getUsersMap(): JSONObject {
        val jsonString = prefs.getString(KEY_USERS_JSON, "{}")
        return JSONObject(jsonString!!)
    }

    private fun saveUsersMap(map: JSONObject) {
        prefs.edit().putString(KEY_USERS_JSON, map.toString()).apply()
    }

    fun saveUser(user: User) {
        prefs.edit()
            .putInt(KEY_USER_ID, user.id)
            .putString(KEY_USER_NAME, user.name)
            .putString(KEY_ROLE, user.role.name)
            .putBoolean(KEY_LOGGED_IN, true)
            .putBoolean(KEY_GUEST, false)
            .apply()
    }

    fun registerUser(credential: String, pass: String): Boolean {
        val users = getUsersMap()
        if (users.has(credential)) {
            // Overwriting existing user for simplicity during testing
        }
        val userObj = JSONObject()
        userObj.put("password", pass)
        userObj.put("name", "")
        userObj.put("id", (1..1000).random())
        users.put(credential, userObj)
        saveUsersMap(users)

        // Log them in immediately after registration
        prefs.edit()
            .putString(KEY_CURRENT_CREDENTIAL, credential)
            .putInt(KEY_USER_ID, userObj.getInt("id"))
            .putString(KEY_USER_NAME, "")
            .putString(KEY_ROLE, Role.PROMOTER.name)
            .putBoolean(KEY_LOGGED_IN, true)
            .putBoolean(KEY_GUEST, false)
            .apply()

        return true
    }

    fun validateLogin(credential: String, pass: String): Boolean {
        val users = getUsersMap()
        if (users.has(credential)) {
            val userObj = users.getJSONObject(credential)
            if (userObj.getString("password") == pass) {
                prefs.edit()
                    .putString(KEY_CURRENT_CREDENTIAL, credential)
                    .putInt(KEY_USER_ID, userObj.getInt("id"))
                    .putString(KEY_USER_NAME, userObj.optString("name", ""))
                    .putString(KEY_ROLE, Role.PROMOTER.name)
                    .putBoolean(KEY_LOGGED_IN, true)
                    .putBoolean(KEY_GUEST, false)
                    .apply()
                return true
            }
        }
        return false
    }

    fun updateUserName(name: String) {
        // Update current session
        prefs.edit().putString(KEY_USER_NAME, name).apply()
        
        // Update persistent users map so it's remembered on next login
        val currentCred = prefs.getString(KEY_CURRENT_CREDENTIAL, null)
        if (currentCred != null) {
            val users = getUsersMap()
            if (users.has(currentCred)) {
                val userObj = users.getJSONObject(currentCred)
                userObj.put("name", name)
                saveUsersMap(users)
            }
        }
    }

    fun getUser(): User? {
        if (!isLoggedIn()) return null
        val id = prefs.getInt(KEY_USER_ID, -1)
        val name = prefs.getString(KEY_USER_NAME, null) ?: return null
        val roleName = prefs.getString(KEY_ROLE, Role.PROMOTER.name) ?: Role.PROMOTER.name
        val role = Role.valueOf(roleName)
        return User(id, name, role)
    }

    fun setGuest(guest: Boolean) {
        prefs.edit().putBoolean(KEY_GUEST, guest).apply()
        if (guest) prefs.edit().putBoolean(KEY_LOGGED_IN, false).apply()
    }

    fun isGuest(): Boolean = prefs.getBoolean(KEY_GUEST, false)
    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_LOGGED_IN, false)

    fun clearUser() {
        // Clear current session, but leave KEY_USERS_JSON intact
        prefs.edit()
            .remove(KEY_USER_ID)
            .remove(KEY_USER_NAME)
            .remove(KEY_ROLE)
            .remove(KEY_LOGGED_IN)
            .remove(KEY_GUEST)
            .remove(KEY_CURRENT_CREDENTIAL)
            .apply()
    }

    companion object {
        private const val KEY_USER_ID = "key_user_id"
        private const val KEY_USER_NAME = "key_user_name"
        private const val KEY_ROLE = "key_role"
        private const val KEY_LOGGED_IN = "key_logged_in"
        private const val KEY_GUEST = "key_guest"
        private const val KEY_CURRENT_CREDENTIAL = "key_current_credential"
        private const val KEY_USERS_JSON = "key_users_json"

        @Volatile
        private var INSTANCE: SharedPrefManager? = null

        fun getInstance(context: Context): SharedPrefManager {
            return INSTANCE ?: synchronized(this) {
                val inst = SharedPrefManager(context.applicationContext)
                INSTANCE = inst
                inst
            }
        }
    }
}
