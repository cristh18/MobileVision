package com.tolodev.mobilevision.manager

import android.content.Context
import android.content.SharedPreferences
import com.tolodev.mobilevision.application.MobileVisionApplication

class PreferencesManager {
    companion object {

        @Volatile
        private var preferencesInstance: PreferencesManager? = null

        var sharedPreferences: SharedPreferences

        fun getInstance(): PreferencesManager {
            return if (preferencesInstance != null) {
                preferencesInstance as PreferencesManager
            } else {
                initPreferenceManager()
            }
        }

        fun initPreferenceManager() = PreferencesManager()

        init {
            sharedPreferences = MobileVisionApplication.app.applicationContext.getSharedPreferences("LAUNCH_ICON", Context.MODE_PRIVATE)
        }

    }

    fun set(key: String, value: Any) {
        val editor = getEditor()
//        editor.putString(key, Gson().toJson(`object`))
//        editor.putString(key, value.toString())
        editor.putBoolean(key, value as Boolean)
        editor.commit()
    }

    fun getBoolean(preferenceKey: String): Boolean {

        return sharedPreferences.getBoolean(preferenceKey, false)
    }

    private fun getEditor(): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }
}