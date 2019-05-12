package com.valer.sergey.imageprocessor.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.valer.sergey.imageprocessor.R
import com.valer.sergey.imageprocessor.extensions.createScaledBitmap
import com.valer.sergey.imageprocessor.presentation.base.BaseFragment
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.IOException
import java.io.InputStream

class RequestImageManager (
        private val fragment: BaseFragment
) {

    companion object {
        const val REQUEST_IMAGE_CAMERA = 1001
        const val REQUEST_IMAGE_GALLERY = 1002
        const val PERMISSIONS_REQUEST_CAMERA = 4
        const val PERMISSIONS_REQUEST_STORAGE = 8
        const val INTENT_IMAGE_TYPE = "image/*"
        const val FILE_PROVIDER = ".fileProvider"
        const val FILE_PROVIDER_IMAGES = "images"
        const val FILE_NAME_PREFIX = "temp_"
        const val FILE_NAME_EXTENSION = ".jpg"
    }

    private lateinit var currentPhotoPath: String
    private lateinit var onLocalImageAction: (Bitmap) -> Unit
    private lateinit var onCameraImageAction: (Bitmap) -> Unit

    fun requestFromGallery(localImageAction: (Bitmap) -> Unit) {
        onLocalImageAction = localImageAction
        fragment.context?.let {
            val permissionExternalStorage = ContextCompat.checkSelfPermission(it, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            if (!permissionExternalStorage) {
                fragment.requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSIONS_REQUEST_STORAGE)
            } else {
                val intent = Intent(Intent.ACTION_PICK).apply {
                    putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    type = INTENT_IMAGE_TYPE
                }
                val chooser = Intent.createChooser(intent, it.getString(R.string.choose_pic))
                fragment.startActivityForResult(chooser, REQUEST_IMAGE_GALLERY)
            }
        }
    }

    fun requestFromCamera(cameraAction: (Bitmap) -> Unit) {
        onCameraImageAction = cameraAction
        fragment.context?.let {
            val permissionCamera = ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            if (!permissionCamera) {
                fragment.requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSIONS_REQUEST_CAMERA)
            } else {
                launchSystemCamera(fragment, createImageFile(it), REQUEST_IMAGE_CAMERA)
            }
        }
    }

    private fun createImageFile(context: Context): File {
        val cachePath = getImageCacheDir(context)
        val imageFileName = FILE_NAME_PREFIX + System.currentTimeMillis() + FILE_NAME_EXTENSION
        val newFile = File(cachePath, imageFileName)
        try {
            newFile.createNewFile()
            newFile.deleteOnExit()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        currentPhotoPath = newFile.absolutePath
        return newFile
    }

    private fun getImageCacheDir(context: Context): File {
        val dir = File(context.cacheDir, FILE_PROVIDER_IMAGES)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    private fun launchSystemCamera(fragment: BaseFragment, file: File, code: Int) {
        fragment.context?.let {
            val outputUri: Uri = FileProvider.getUriForFile(it, it.packageName + FILE_PROVIDER, file)
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            fragment.startActivityForResult(intent, code)
        }
    }

    fun onRequestPermissionsResult(requestCode: Int) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CAMERA -> {
                fragment.context?.let {
                    val permissionCamera = ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    if (permissionCamera) requestFromCamera(onCameraImageAction)
                }

            }
            PERMISSIONS_REQUEST_STORAGE -> {
                fragment.context?.let {
                    val permissionExternalStorage = ContextCompat.checkSelfPermission(it, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    if (permissionExternalStorage) requestFromGallery(onLocalImageAction)
                }
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_GALLERY -> {
                var imageStream: InputStream? = null
                val imageUri: Uri? = data?.data
                imageUri?.let {
                    imageStream = fragment.context?.contentResolver?.openInputStream(imageUri)
                }
                if (imageStream != null) {
                    Single.defer { Single.just(BitmapFactory.decodeStream(imageStream))}
                            .flatMap { Single.just(it.createScaledBitmap()) }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                onLocalImageAction.invoke(it)
                            }, {
                                showErrorToast()
                            })
                } else {
                    showErrorToast()
                }
            }
            resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CAMERA -> {
                Single.defer { Single.just(BitmapFactory.decodeFile(currentPhotoPath)) }
                        .flatMap { Single.just(it.createScaledBitmap()) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            onCameraImageAction.invoke(it)
                        }, {
                            showErrorToast()
                        })
            }
        }
    }

    private fun showErrorToast() {
        fragment.context?.let {
            Toast.makeText(it, R.string.error_text, Toast.LENGTH_SHORT).show()
        }
    }
}