package com.valer.sergey.imageprocessor.presentation.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.valer.sergey.imageprocessor.R

const val PROGRESS_DIALOG_TAG = "PROGRESS_DIALOG_TAG"

class ProgressDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflater = LayoutInflater.from(context)
        val dialogView = layoutInflater.inflate(R.layout.progress_dialog, null)
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.apply {
            setView(dialogView)
        }
        return alertDialog
    }

}