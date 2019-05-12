package com.valer.sergey.imageprocessor.presentation.presenters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.valer.sergey.imageprocessor.app.App
import com.valer.sergey.imageprocessor.data.ImagePickerState
import com.valer.sergey.imageprocessor.data.ImageProcessingState
import com.valer.sergey.imageprocessor.extensions.createScaledBitmap
import com.valer.sergey.imageprocessor.extensions.mirror
import com.valer.sergey.imageprocessor.extensions.rotate
import com.valer.sergey.imageprocessor.extensions.toBlackAndWhite
import com.valer.sergey.imageprocessor.repository.ImageProcessingRepository
import com.valer.sergey.imageprocessor.presentation.base.BasePresenter
import com.valer.sergey.imageprocessor.presentation.contracts.ImageProcessingContract
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ImageProcessingPresenter: BasePresenter<ImageProcessingContract.View>(), ImageProcessingContract.Presenter {

    private lateinit var view: ImageProcessingContract.View

    override var processingState: ImageProcessingState = ImageProcessingState()

    @Inject
    lateinit var imageProcessingRepository: ImageProcessingRepository

    override var currentBitmap: Bitmap? = null
        set(value) {
            field = value
            processingState.currentBitmap = field
            Log.w("meh", "set bitmap")
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
            imageProcessingRepository.loadImage(address)
                    .doOnSubscribe { view.showProgress(true) }
                    .doFinally { view.showProgress(false) }
                    .flatMap {
                        val bitmap = BitmapFactory.decodeStream(it.byteStream())
                        Single.just(bitmap.createScaledBitmap())
                    }
                    .subscribe({
                        currentBitmap = it
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
        currentBitmap?.let{ currBitmap ->
            updateList(action.invoke(currBitmap))
        }
    }

    private fun updateList(bitmap: Bitmap) {
        var newItem: Pair<Int, Bitmap?> = 100 to bitmap
        processingState.processedItems.add(newItem)
        var item2 = processingState.processedItems.lastIndex
        val random = Random().nextInt(25) + 5
        Log.w("meh", "random = " + random)
        val range = Observable.range(1, random)
        val interval = Observable.interval(1, TimeUnit.SECONDS)
        Observables.zip(
                range,
                interval
        ) { range, _ ->
            val multiplier = (100 / random) * range
            Log.w("meh", "func " + range)
            if (range == random) 100 else multiplier
        }.subscribe({
                    Log.w("meh", "func final" + it.toString())

                    view.addProcessedItem(newItem)
                }, {}).addTo(binds)



    }
}