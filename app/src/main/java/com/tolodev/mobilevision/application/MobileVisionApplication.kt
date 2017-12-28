package com.tolodev.mobilevision.application

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.tolodev.mobilevision.util.CrashReportingTree
import com.tolodev.mobilevision.util.DeviceUtil
import io.fabric.sdk.android.Fabric
import timber.log.BuildConfig
import timber.log.Timber

class MobileVisionApplication : Application() {
    companion object {
        @JvmStatic
        lateinit var app: MobileVisionApplication
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        DeviceUtil.initDeviceParams(this)
        initCrashlytics()
        initTimber()
    }

    private fun initCrashlytics() {
        val core = CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build()
        Fabric.with(this, Crashlytics.Builder().core(core).build())
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.plant(CrashReportingTree())
    }
}