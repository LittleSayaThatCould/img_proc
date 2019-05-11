package com.valer.sergey.imageprocessor.presentation.fragments

import android.content.Intent
import android.graphics.Bitmap
import com.valer.sergey.imageprocessor.R
import com.valer.sergey.imageprocessor.app.App
import com.valer.sergey.imageprocessor.presentation.base.BaseFragment
import com.valer.sergey.imageprocessor.presentation.contracts.ImageProcessingContract
import com.valer.sergey.imageprocessor.utils.RequestImageManager
import kotlinx.android.synthetic.main.frag_img_proc.*
import javax.inject.Inject


class ImageProcessingFragment : BaseFragment(), ImageProcessingContract.View {

    @Inject
    lateinit var presenter: ImageProcessingContract.Presenter

    private var requestImageManager = RequestImageManager(this) { frag_img_proc_main_img.setImageBitmap(it) }

    override val layoutRes: Int
        get() = R.layout.frag_img_proc

    override fun injectDependecy() {
        App.components.appComponent.inject(this)
    }

    override fun onAttachInit() {
        presenter.onAttach(this)
        frag_img_proc_mirror.setOnClickListener { requestImageManager.requestFromCamera() }
        frag_img_proc_rotate.setOnClickListener{ requestImageManager.requestFromGallery() }
    }

    override fun onDestroyInit() {
        presenter.unSubscribe()
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