package com.tolodev.mobilevision.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

class DeviceUtil {

    companion object {

        var diagonalInches = 0.0

        fun initDeviceParams(context: Context) {
            val metrics = DisplayMetrics()

            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(metrics)

            val widthPixels = metrics.widthPixels
            val heightPixels = metrics.heightPixels
            val widthDpi = metrics.xdpi
            val heightDpi = metrics.ydpi
            val widthInches = widthPixels / widthDpi
            val heightInches = heightPixels / heightDpi
            diagonalInches = Math.sqrt(widthInches.toDouble() * widthInches + heightInches * heightInches)
        }

        fun isTablet(): Boolean = diagonalInches >= 7

    }

}