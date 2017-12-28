package com.tolodev.mobilevision.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.view.View
import android.widget.Toast
import com.tolodev.mobilevision.R
import com.tolodev.mobilevision.databinding.ActivityMainBinding
import com.tolodev.mobilevision.manager.PermissionManager
import com.tolodev.mobilevision.util.BitmapUtils
import timber.log.Timber
import java.io.IOException

class MainActivity : BaseActivity() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val FILE_PROVIDER_AUTHORITY = "com.example.android.fileprovider"

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var temporalPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    fun clearImage(view: View) {

    }

    fun saveMe(view: View) {

    }

    fun emojifyMe(view: View) {
        if (PermissionManager.isPermissionsGranted(this, PermissionManager.REQUEST_STORAGE_PERMISSION, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            launchCamera()
        }
    }

    fun shareMe(view: View) {

    }

    private fun launchCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (takePictureIntent.resolveActivity(packageManager) != null) {

            try {
                val photoFile = BitmapUtils.createTempImageFile(this)
                temporalPhotoPath = photoFile.absolutePath
                val photoURI = FileProvider.getUriForFile(this, FILE_PROVIDER_AUTHORITY, photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (ex: IOException) {
                Timber.e(ex, ex.localizedMessage)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            processAndSetImage()
        } else {
            BitmapUtils.deleteImageFile(this, temporalPhotoPath)
        }
    }

    private fun processAndSetImage() {
        activityMainBinding.emojifyButton.visibility = View.GONE
        activityMainBinding.titleTextView.visibility = View.GONE
        activityMainBinding.saveButton.visibility = View.VISIBLE
        activityMainBinding.shareButton.visibility = View.VISIBLE
        activityMainBinding.clearButton.visibility = View.VISIBLE

        val photoTaken = BitmapUtils.resamplePic(this, temporalPhotoPath)
        activityMainBinding.imageView.setImageBitmap(photoTaken)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.manageRequestPermissionResult(requestCode, grantResults,
                object : PermissionManager.ResultPermissionActionGrant {
                    override fun onGrant(grantedRequestCode: Int) {
                        checkGrantedPermission(grantedRequestCode)
                    }
                },
                object : PermissionManager.ResultPermissionActionDenied {
                    override fun onDenied(deniedRequestCode: Int, context: Context) {
                        Toast.makeText(context, R.string.permission_denied, Toast.LENGTH_SHORT).show()
                    }
                }, this)
    }

    private fun checkGrantedPermission(grantedRequestCode: Int) {
        if (grantedRequestCode == PermissionManager.REQUEST_STORAGE_PERMISSION) {
            launchCamera()
        }
    }
}
