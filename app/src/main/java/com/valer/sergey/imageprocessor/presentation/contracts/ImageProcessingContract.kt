package com.valer.sergey.imageprocessor.presentation.contracts

import android.graphics.Bitmap
import com.valer.sergey.imageprocessor.data.ImagePickerState
import com.valer.sergey.imageprocessor.data.ImageProcessingState
import com.valer.sergey.imageprocessor.presentation.base.BaseContract

class ImageProcessingContract {
    interface View: BaseContract.View {
        fun setImage(bitmap: Bitmap)
        fun isDialogShowing(isShowing: Boolean)
        fun showErrorLoading()
        fun showProgress(isInProgress: Boolean)
        fun showProcessedItems(list: MutableList<Bitmap>)
        fun addProcessedItem(bitmap: Bitmap)
    }

    interface Presenter: BaseContract.Presenter<ImageProcessingContract.View> {
        fun rotate()
        fun mirrorImage()
        fun invertColors()
        fun loadImage(address: String)
        var currentBitmap: Bitmap?
        var dialogState: ImagePickerState
        var processingState: ImageProcessingState
    }
}