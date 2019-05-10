package com.valer.sergey.imageprocessor.presentation.fragments

import com.valer.sergey.imageprocessor.R
import com.valer.sergey.imageprocessor.app.App
import com.valer.sergey.imageprocessor.presentation.base.BaseFragment
import com.valer.sergey.imageprocessor.presentation.contracts.ImageProcessingContract
import kotlinx.android.synthetic.main.frag_img_proc.*
import javax.inject.Inject


class ImageProcessingFragment : BaseFragment(), ImageProcessingContract.View {

    @Inject
    lateinit var presenter: ImageProcessingContract.Presenter

    override val layoutRes: Int
        get() = R.layout.frag_img_proc

    override fun injectDependecy() {
        App.components.appComponent.inject(this)
    }

    override fun onAttachInit() {
        presenter.onAttach(this)
        frag_img_proc_mirror.setOnClickListener { presenter.incrementRotate() }
    }

    override fun updateRotate(value: Int) {
        frag_img_proc_rotate.text = "Value is $value"
    }

    override fun onDestroyInit() {
        presenter.unSubscribe()
    }
}