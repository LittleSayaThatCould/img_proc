package com.valer.sergey.imageprocessor.presentation.presenters

import android.graphics.Bitmap
import android.util.Log
import com.valer.sergey.imageprocessor.app.App
import com.valer.sergey.imageprocessor.domain.repository.ImageProcessingRepository
import com.valer.sergey.imageprocessor.presentation.base.BasePresenter
import com.valer.sergey.imageprocessor.presentation.contracts.ImageProcessingContract
import javax.inject.Inject

class ImageProcessingPresenter: BasePresenter<ImageProcessingContract.View>(), ImageProcessingContract.Presenter {

    private lateinit var view: ImageProcessingContract.View

    @Inject
    lateinit var imageProcessingRepository: ImageProcessingRepository

    override fun subscribe() {
        binds.addAll(

        )
    }

    init {
        App.components.appComponent.inject(this)
    }

    override fun onAttach(view: ImageProcessingContract.View) {
        Log.w("meh", "image presenter attached")
        this.view = view
        imageProcessingRepository.loadImage()
    }

    override fun rotate(bitmap: Bitmap): Bitmap = bitmap

    override fun mirrorImage(bitmap: Bitmap): Bitmap = bitmap

    override fun invertColors(bitmap: Bitmap): Bitmap = bitmap
}