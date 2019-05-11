package com.valer.sergey.imageprocessor.presentation.contracts

import android.graphics.Bitmap
import com.valer.sergey.imageprocessor.presentation.base.BaseContract

class ImageProcessingContract {
    interface View: BaseContract.View

    interface Presenter: BaseContract.Presenter<ImageProcessingContract.View> {
        fun rotate(bitmap: Bitmap): Bitmap
        fun mirrorImage(bitmap: Bitmap): Bitmap
        fun invertColors(bitmap: Bitmap): Bitmap
    }
}