package com.valer.sergey.imageprocessor.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(), BaseContract.View {

    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependecy()
    }

    abstract fun injectDependecy()
    abstract fun onAttachInit()
    abstract fun onDestroyInit()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutRes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onAttachInit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        onDestroyInit()
    }
}