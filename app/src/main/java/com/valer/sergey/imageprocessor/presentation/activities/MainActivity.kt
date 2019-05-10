package com.valer.sergey.imageprocessor.presentation.activities

import android.os.Bundle
import com.valer.sergey.imageprocessor.R
import com.valer.sergey.imageprocessor.app.App
import com.valer.sergey.imageprocessor.presentation.base.BaseActivity
import com.valer.sergey.imageprocessor.presentation.contracts.MainContract
import com.valer.sergey.imageprocessor.presentation.fragments.ImageProcessingFragment
import javax.inject.Inject


class MainActivity : BaseActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    override val layoutRes: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onAttach(this)
    }

    override fun injectDependency() {
        App.components.appComponent.inject(this)
    }

    override fun showImageProcessingFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frag_container, ImageProcessingFragment())
                .commitAllowingStateLoss()
    }
}
