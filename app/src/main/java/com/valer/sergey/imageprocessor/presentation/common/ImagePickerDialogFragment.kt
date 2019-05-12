package com.valer.sergey.imageprocessor.presentation.common

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.valer.sergey.imageprocessor.R
import com.valer.sergey.imageprocessor.data.ImagePickerState
import com.valer.sergey.imageprocessor.data.fromBundle
import kotlinx.android.synthetic.main.image_picker.view.*

const val IMAGE_PICKER_TAG = "IMAGE_PICKER_TAG"

class ImagePickerDialogFragment : DialogFragment() {

    private var listener: PickerListener? = null
    private var pickerState: ImagePickerState = ImagePickerState()

    fun setPickerListener(pickerListener: PickerListener) {
        listener = pickerListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflater = LayoutInflater.from(context)
        val dialogView = layoutInflater.inflate(R.layout.image_picker, null)
        pickerState = ImagePickerState.fromBundle(arguments)
        val alertDialog = AlertDialog.Builder(context).create()
        fun closeAlertDialog() {
            pickerState.isShowing = false
            alertDialog.dismiss()
            dismiss()
        }
        dialogView.apply {
            image_picker_address.setText(pickerState.currentAddress)
            image_picker_address.setSelection(pickerState.currentAddress.length)

            image_picker_address.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(p0: Editable?) {
                    pickerState.currentAddress = dialogView.image_picker_address.text.toString()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            image_picker_local.setOnClickListener {
                listener?.let { it.onLocalImagePick() }
                closeAlertDialog()
            }

            image_picker_camera.setOnClickListener {
                listener?.let { it.onCameraPick() }
                closeAlertDialog()
            }

            image_picker_ok.setOnClickListener {
                if (image_picker_address.text.toString().isNotEmpty()) {
                    listener?.let { it.onLoadFromUrlPick(dialogView.image_picker_address.text.toString()) }
                    closeAlertDialog()
                } else {
                    Toast.makeText(this@ImagePickerDialogFragment.context, R.string.error_url_empty, Toast.LENGTH_SHORT).show()
                }
            }

        }

        alertDialog.apply {
            setView(dialogView)
            pickerState.isShowing = true
        }
        return alertDialog
    }

    private fun updatePickerState() {
        listener?.updatePickerState(pickerState)
    }

    override fun onCancel(dialog: DialogInterface) {
        pickerState.isShowing = false
        super.onCancel(dialog)
    }

    override fun onDismiss(dialog: DialogInterface) {
        updatePickerState()
        super.onDismiss(dialog)
    }
}

interface PickerListener {
    fun onLocalImagePick()
    fun onCameraPick()
    fun onLoadFromUrlPick(address: String)
    fun updatePickerState(pickerState: ImagePickerState)
}