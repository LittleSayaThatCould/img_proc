package com.valer.sergey.imageprocessor.presentation.contract

import com.valer.sergey.imageprocessor.presentation.base.BaseContract

class ImageProcessingContract {
    interface View: BaseContract.View {
        fun updateRotate(value: Int)
    }

    interface Presenter: BaseContract.Presenter<ImageProcessingContract.View> {
        fun incrementRotate()
    }
}