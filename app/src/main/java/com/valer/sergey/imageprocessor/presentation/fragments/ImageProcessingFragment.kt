package com.valer.sergey.imageprocessor.presentation.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.valer.sergey.imageprocessor.R
import com.valer.sergey.imageprocessor.app.App
import com.valer.sergey.imageprocessor.data.ImagePickerState
import com.valer.sergey.imageprocessor.data.bundle
import com.valer.sergey.imageprocessor.presentation.base.BaseFragment
import com.valer.sergey.imageprocessor.presentation.common.*
import com.valer.sergey.imageprocessor.presentation.contracts.ImageProcessingContract
import com.valer.sergey.imageprocessor.presentation.fragments.adapters.ProcessingAdapter
import com.valer.sergey.imageprocessor.utils.RequestImageManager
import kotlinx.android.synthetic.main.frag_img_proc.*
import javax.inject.Inject


class ImageProcessingFragment : BaseFragment(), ImageProcessingContract.View {

    @Inject
    lateinit var presenter: ImageProcessingContract.Presenter

    private var requestImageManager = RequestImageManager(this)

    override val layoutRes: Int
        get() = R.layout.frag_img_proc

    override fun injectDependecy() {
        App.components.appComponent.inject(this)
    }

    override fun onAttachInit() {
        presenter.onAttach(this)
        frag_img_proc_mirror.setOnClickListener { presenter.mirrorImage() }
        frag_img_proc_rotate.setOnClickListener { presenter.rotate() }
        frag_img_proc_invert.setOnClickListener { presenter.invertColors() }
        frag_img_proc_main_img.setOnClickListener { showRequestImagePicker() }
        frag_img_proc_main_choose_pic.setOnClickListener { showRequestImagePicker() }
        initChooseTextVisibility()
        initProcessedItemsView()
    }

    private fun initProcessedItemsView() {
        val mutableList = mutableListOf<Bitmap>()
        mutableList.addAll(presenter.processingState.processedItems)
        frag_img_proc_results.apply {
            adapter = ProcessingAdapter(
                    mutableList,
                    { presenter.currentBitmap = it },
                    { presenter.processingState.processedItems.removeAt(it)}
            )
            layoutManager = LinearLayoutManager(context)
            smoothScrollToPosition(presenter.processingState.scrolledPosition)
        }
    }

    private fun initChooseTextVisibility() {
        if (presenter.currentBitmap != null) {
            frag_img_proc_main_img.visibility = View.VISIBLE
            frag_img_proc_main_choose_pic.visibility = View.GONE
        } else {
            frag_img_proc_main_img.visibility = View.GONE
            frag_img_proc_main_choose_pic.visibility = View.VISIBLE        }
    }

    override fun addProcessedItem(bitmap: Bitmap) {
        val adapter = frag_img_proc_results.adapter as? ProcessingAdapter
        adapter?.let{
            it.addItem(bitmap)
            frag_img_proc_results.smoothScrollToPosition(it.items.lastIndex)
        }
    }

    override fun showProcessedItems(list: MutableList<Bitmap>) {
        if (list.isNotEmpty()) {
            val adapter = frag_img_proc_results.adapter as? ProcessingAdapter
            adapter?.items = list
        }
    }

    private fun showRequestImagePicker() {
        val processingDialog = ImagePickerDialogFragment().apply {
            arguments = presenter.dialogState.bundle
            setPickerListener(object : PickerListener{
                override fun onLocalImagePick() {
                    requestImageManager.requestFromGallery{ presenter.currentBitmap = it }
                }

                override fun onCameraPick() {
                    requestImageManager.requestFromCamera{ presenter.currentBitmap = it }
                }

                override fun onLoadFromUrlPick(address: String) {
                    presenter.loadImage(address)
                }

                override fun updatePickerState(pickerState: ImagePickerState) {
                    presenter.dialogState = pickerState
                }
            })
        }
        processingDialog.show(childFragmentManager, IMAGE_PICKER_TAG)
    }

    override fun onDestroyInit() {
        val layoutManager = frag_img_proc_results.layoutManager as? LinearLayoutManager
        layoutManager?.let {
            if (layoutManager.findFirstVisibleItemPosition() >= 0) {
                presenter.processingState.scrolledPosition = layoutManager.findFirstVisibleItemPosition()
            }
        }
        presenter.unSubscribe()
    }

    override fun setImage(bitmap: Bitmap) {
        initChooseTextVisibility()
        frag_img_proc_main_img.setImageBitmap(bitmap)
    }

    override fun isDialogShowing(isShowing: Boolean) {
        if (isShowing) showRequestImagePicker()
    }

    override fun showErrorLoading() {
        Toast.makeText(context, R.string.error_load_text, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress(isInProgress: Boolean) {
        val dialog = childFragmentManager.findFragmentByTag(PROGRESS_DIALOG_TAG) as? ProgressDialog ?: ProgressDialog().apply { /*isCancelable = false*/ }
        if (isInProgress) dialog.show(childFragmentManager, PROGRESS_DIALOG_TAG) else dialog.dismiss()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        requestImageManager.onRequestPermissionsResult(requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        requestImageManager.onActivityResult(requestCode, resultCode, data)
    }
}