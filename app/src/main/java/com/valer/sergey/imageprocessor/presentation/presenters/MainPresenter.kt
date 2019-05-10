package com.valer.sergey.imageprocessor.presentation.presenters


import com.valer.sergey.imageprocessor.presentation.contracts.MainContract

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