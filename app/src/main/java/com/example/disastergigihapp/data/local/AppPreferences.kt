package com.example.disastergigihapp.data.local

import android.content.Context
import androidx.preference.PreferenceManager

object AppPreferences {

    private const val KEY_DARK_MODE = "dark_mode"

    fun getDarkModePreference(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(KEY_DARK_MODE, false)
    }

    fun setDarkModePreference(context: Context, darkModeEnabled: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putBoolean(KEY_DARK_MODE, darkModeEnabled).apply()
    }
}