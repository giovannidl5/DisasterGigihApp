package com.example.disastergigihapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.disastergigihapp.data.local.AppPreferences
import com.example.disastergigihapp.databinding.ActivityDarkModeBinding

class DarkMode : AppCompatActivity() {
    private lateinit var darkModeBinding: ActivityDarkModeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        darkModeBinding = ActivityDarkModeBinding.inflate(layoutInflater)
        setContentView(darkModeBinding.root)

        darkModeBinding.buttonSettingsBack.setOnClickListener { finish() }

        darkModeBinding.switchDarkMode.isChecked = AppPreferences.getDarkModePreference(this)

        darkModeBinding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            AppPreferences.setDarkModePreference(this, isChecked)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            recreate() // Restart the activity to apply the new theme
        }
    }
}