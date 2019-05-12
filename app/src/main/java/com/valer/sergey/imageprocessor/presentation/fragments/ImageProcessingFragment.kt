package com.valer.sergey.imageprocessor.presentation.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import com.valer.sergey.imageprocessor.R
import com.valer.sergey.imageprocessor.app.App
import com.valer.sergey.imageprocessor.data.ImagePickerState
import com.valer.sergey.imageprocessor.data.bundle
import com.valer.sergey.imageprocessor.presentation.base.BaseFragment
import com.valer.sergey.imageprocessor.presentation.common.IMAGE_PICKER_TAG
import com.valer.sergey.imageprocessor.presentation.common.ImagePickerDialogFragment
import com.valer.sergey.imageprocessor.presentation.common.PickerListener
import com.valer.sergey.imageprocessor.presentation.contracts.ImageProcessingContract
import com.valer.sergey.imageprocessor.utils.RequestImageManager
import kotlinx.android.synthetic.main.frag_img_proc.*
import javax.inject.Inject


class ImageProcessingFragment : BaseFragment(), ImageProcessingContract.View {

    @Inject
    lateinit var presenter: ImageProcessingContract.Presenter

    private var requestImageManager = RequestImageManager(this)

    override val layoutRes: Int
        get() = R.layout.frag_img_proc

    override fun injectDependecy() {
        App.components.appComponent.inject(this)
    }

    override fun onAttachInit() {
        frag_img_proc_mirror.setOnClickListener { presenter.mirrorImage() }
        frag_img_proc_rotate.setOnClickListener { presenter.rotate() }
        frag_img_proc_invert.setOnClickListener { presenter.invertColors() }
        frag_img_proc_main_img.setOnClickListener { showRequestImagePicker() }
        presenter.onAttach(this)
    }

    private fun showRequestImagePicker() {
        val processingDialog = ImagePickerDialogFragment().apply {
            arguments = presenter.dialogState.bundle
            setPickerListener(object : PickerListener{
                override fun onLocalImagePick() {
                    requestImageManager.requestFromGallery{ presenter.curentBitmap = it }
                }

                override fun onCameraPick() {
                    requestImageManager.requestFromCamera{ presenter.curentBitmap = it }
                }

                override fun onLoadFromUrlPick(address: String) {
                    presenter.loadImage(address)
                }

                override fun updatePickerState(pickerState: ImagePickerState) {
                    presenter.dialogState = pickerState
                }
            })
        }
        processingDialog.show(childFragmentManager, IMAGE_PICKER_TAG)
    }

    override fun onDestroyInit() {
        presenter.unSubscribe()
    }

    override fun setImage(bitmap: Bitmap) {
        frag_img_proc_main_img.setImageBitmap(bitmap)
    }

    override fun isDialogShowing(isShowing: Boolean) {
        if (isShowing) showRequestImagePicker()
    }

    override fun showErrorLoading() {
        Toast.makeText(context, R.string.error_load_text, Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        requestImageManager.onRequestPermissionsResult(requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        requestImageManager.onActivityResult(requestCode, resultCode, data)
    }
}