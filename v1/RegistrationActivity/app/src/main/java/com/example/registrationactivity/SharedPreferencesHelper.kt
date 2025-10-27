package com.example.registrationactivity

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    fun saveUser(username: String, email: String, password: String) {
        val editor = prefs.edit()
        editor.putString("${email}_username", username)
        editor.putString("${email}_password", password)
        editor.apply()
    }

    fun userExists(email: String): Boolean {
        return prefs.contains("${email}_username")
    }

    fun getPassword(email: String): String? {
        return prefs.getString("${email}_password", null)
    }

    fun getUsername(email: String): String? {
        return prefs.getString("${email}_username", null)
    }
}
