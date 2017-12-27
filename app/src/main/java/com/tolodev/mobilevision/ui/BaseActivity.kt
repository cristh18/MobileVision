package com.tolodev.mobilevision.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.tolodev.mobilevision.util.DeviceUtil

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOrientation()
    }

    protected fun addFragment(containerId: Int, fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
                .add(containerId, fragment)
                .commit()
    }

    private fun setOrientation() {
        requestedOrientation = if (DeviceUtil.isTablet()) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

}