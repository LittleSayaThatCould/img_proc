package com.valer.sergey.imageprocessor.presentation.contracts

import com.valer.sergey.imageprocessor.presentation.base.BaseContract

class MainContract {
    interface View: BaseContract.View {
        fun showImageProcessingFragment()
    }
    interface Presenter: BaseContract.Presenter<View>
}