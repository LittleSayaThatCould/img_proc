package com.valer.sergey.imageprocessor.presentation.base

class BaseContract {
    interface View
    interface Presenter<in T> {
        fun subscribe()
        fun unSubscribe()
        fun onAttach(view: T)
    }
}