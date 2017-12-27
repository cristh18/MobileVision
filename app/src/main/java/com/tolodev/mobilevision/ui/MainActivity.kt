package com.tolodev.mobilevision.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.view.View
import android.widget.Toast
import com.tolodev.mobilevision.R
import com.tolodev.mobilevision.databinding.ActivityMainBinding
import com.tolodev.mobilevision.util.BitmapUtils
import java.io.File
import java.io.IOException

class MainActivity : BaseActivity() {

    private val REQUEST_STORAGE_PERMISSION = 1
    private val REQUEST_IMAGE_CAPTURE = 2
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
        Toast.makeText(this, "Take photo", Toast.LENGTH_LONG).show()

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_STORAGE_PERMISSION)
        } else {
            launchCamera()
        }
    }

    fun shareMe(view: View) {

    }

    private fun launchCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (takePictureIntent.resolveActivity(packageManager) != null) {

            var photoFile: File? = null
            try {
                photoFile = BitmapUtils.createTempImageFile(this)
            } catch (ex: IOException) {

                ex.printStackTrace()
            }


            if (photoFile != null) {

                temporalPhotoPath = photoFile.absolutePath
                val photoURI = FileProvider.getUriForFile(this,
                        FILE_PROVIDER_AUTHORITY,
                        photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
}
