package com.tolodev.mobilevision.manager

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

abstract class PermissionManager {

    companion object {
        val REQUEST_STORAGE_PERMISSION = 1

        fun isPermissionsGranted(activity: Activity, permissionRequestCode: Int, permissions: Array<String>): Boolean {
            for (permissionName in permissions) {
                if (ContextCompat.checkSelfPermission(activity, permissionName) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, arrayOf(permissionName), permissionRequestCode)
                    return false
                }
            }
            return true
        }

        fun isPermissionsGranted(fragment: Fragment, permissionRequestCode: Int, permissions: Array<String>): Boolean {
            for (permissionName in permissions) {
                if (ContextCompat.checkSelfPermission(fragment.activity, permissionName) != PackageManager.PERMISSION_GRANTED) {
                    fragment.requestPermissions(arrayOf(permissionName), permissionRequestCode)
                    return false
                }
            }
            return true
        }

        fun manageRequestPermissionResult(permissionRequestCode: Int, grantResults: IntArray, actionGrant: ResultPermissionActionGrant, actionDenied: ResultPermissionActionDenied, context: Context) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                actionGrant.onGrant(permissionRequestCode)
            } else {
                actionDenied.onDenied(permissionRequestCode, context)
            }
        }
    }

    interface ResultPermissionActionGrant {
        fun onGrant(grantedRequestCode: Int)
    }

    interface ResultPermissionActionDenied {
        fun onDenied(deniedRequestCode: Int, context: Context)
    }


}