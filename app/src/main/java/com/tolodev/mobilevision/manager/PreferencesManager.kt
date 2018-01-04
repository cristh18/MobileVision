package com.tolodev.mobilevision.manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.tolodev.mobilevision.application.MobileVisionApplication

class PreferencesManager {
    companion object {

        @Volatile
        private var preferencesInstance: PreferencesManager? = null

        var sharedPreferences: SharedPreferences = MobileVisionApplication.app.applicationContext.getSharedPreferences("LAUNCH_ICON", Context.MODE_PRIVATE)

        fun getInstance(): PreferencesManager {
            return if (preferencesInstance != null) {
                preferencesInstance as PreferencesManager
            } else {
                initPreferenceManager()
            }
        }

        fun initPreferenceManager() = PreferencesManager()

    }

    fun set(key: String, value: Any) {
        val editor = getEditor()
        editor.putBoolean(key, value as Boolean)
        editor.commit()
    }

    fun getBoolean(preferenceKey: String): Boolean = sharedPreferences.getBoolean(preferenceKey, false)

    @SuppressLint("CommitPrefEdits")
    private fun getEditor(): SharedPreferences.Editor = sharedPreferences.edit()

}