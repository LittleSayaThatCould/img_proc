package com.valer.sergey.imageprocessor.presentation.presenters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.valer.sergey.imageprocessor.app.App
import com.valer.sergey.imageprocessor.data.ImagePickerState
import com.valer.sergey.imageprocessor.data.ImageProcessingState
import com.valer.sergey.imageprocessor.extensions.mirror
import com.valer.sergey.imageprocessor.extensions.rotate
import com.valer.sergey.imageprocessor.extensions.toBlackAndWhite
import com.valer.sergey.imageprocessor.repository.ImageProcessingRepository
import com.valer.sergey.imageprocessor.presentation.base.BasePresenter
import com.valer.sergey.imageprocessor.presentation.contracts.ImageProcessingContract
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class ImageProcessingPresenter: BasePresenter<ImageProcessingContract.View>(), ImageProcessingContract.Presenter {

    private lateinit var view: ImageProcessingContract.View
    private val processingState: ImageProcessingState

    @Inject
    lateinit var imageProcessingRepository: ImageProcessingRepository

    override var curentBitmap: Bitmap? = null
        set(value) {
            field = value
            processingState.currentBitmap = field
            field?.let { view.setImage(it) }

        }

    override var dialogState: ImagePickerState = ImagePickerState()

    override fun subscribe() {
        binds.addAll(

        )
    }

    init {
        App.components.appComponent.inject(this)
        processingState = ImageProcessingState()
    }

    override fun onAttach(view: ImageProcessingContract.View) {
        this.view = view
        processingState.currentBitmap?.let { view.setImage(it) }
        view.isDialogShowing(dialogState.isShowing)
    }

    override fun loadImage(address: String) {
        if (address.isNotEmpty()) {
            imageProcessingRepository.loadImage(address).subscribe({
                val bitmap = BitmapFactory.decodeStream(it.byteStream())
                view.setImage(bitmap)
            }, {
                Log.w("meh", "error stuff")
            }).addTo(binds)
        }
    }

    override fun rotate() {
        processBitmap { it.rotate() }
    }

    override fun mirrorImage() {
        processBitmap { it.mirror() }
    }

    override fun invertColors() {
        processBitmap { it.toBlackAndWhite() }
    }

    private fun processBitmap(action: (Bitmap) -> Bitmap) {
        processingState.currentBitmap?.let { bitmap ->
            view.setImage(action.invoke(bitmap))
        }
    }
}