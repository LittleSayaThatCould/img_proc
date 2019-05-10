package com.valer.sergey.imageprocessor.presentation.presenters

import android.util.Log
import com.valer.sergey.imageprocessor.presentation.base.BasePresenter
import com.valer.sergey.imageprocessor.presentation.contracts.ImageProcessingContract

class ImageProcessingPresenter: BasePresenter<ImageProcessingContract.View>(), ImageProcessingContract.Presenter {

    private lateinit var view: ImageProcessingContract.View

    override fun subscribe() {
        binds.addAll(

        )
    }

    override fun onAttach(view: ImageProcessingContract.View) {
        Log.w("meh", "image presenter attached")
        this.view = view
    }

    override fun incrementRotate() {

    }
}