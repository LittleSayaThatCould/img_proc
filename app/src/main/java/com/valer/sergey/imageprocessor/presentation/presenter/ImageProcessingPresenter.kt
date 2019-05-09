package com.valer.sergey.imageprocessor.presentation.presenter

import android.util.Log
import com.valer.sergey.imageprocessor.presentation.contract.ImageProcessingContract

class ImageProcessingPresenter: ImageProcessingContract.Presenter {

    private var value = 0
    private lateinit var view: ImageProcessingContract.View

    override fun subscribe() {

    }

    override fun unSubscribe() {

    }

    override fun onAttach(view: ImageProcessingContract.View) {
        Log.w("meh", "image presenter attached")
        this.view = view
        view.updateRotate(value)
    }

    override fun incrementRotate() {
        view.updateRotate(value)
        value++
    }
}