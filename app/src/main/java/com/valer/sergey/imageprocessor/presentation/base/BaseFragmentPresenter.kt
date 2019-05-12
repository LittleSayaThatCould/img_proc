package com.valer.sergey.imageprocessor.presentation.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {

    val binds: CompositeDisposable = CompositeDisposable()

    override fun unSubscribe() {
        binds.clear()
    }

}