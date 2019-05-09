package com.valer.sergey.imageprocessor.presentation.presenter

import com.valer.sergey.imageprocessor.presentation.contract.MainContract

class MainPresenter: MainContract.Presenter {

    private lateinit var view: MainContract.View

    override fun subscribe() {

    }

    override fun unSubscribe() {

    }

    override fun onAttach(view: MainContract.View) {
        this.view = view
        view.showImageProcessingFragment()
    }
}