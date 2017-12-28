package com.tolodev.mobilevision.util

import android.util.Log
import com.crashlytics.android.Crashlytics
import timber.log.Timber

class CrashReportingTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {

        val CRASHLYTICS_KEY_PRIORITY = "priority"
        val CRASHLYTICS_KEY_TAG = "tag"
        val CRASHLYTICS_KEY_MESSAGE = "message"

        if (priority == Log.ERROR) {
            Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority)
            Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag)
            Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message)

            if (t == null) {
                Crashlytics.logException(Exception(message))
            } else {
                Crashlytics.logException(t)
            }
        }
    }
}