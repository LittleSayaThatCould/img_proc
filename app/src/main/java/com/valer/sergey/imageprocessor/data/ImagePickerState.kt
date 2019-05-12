package com.valer.sergey.imageprocessor.data

import android.os.Bundle
import androidx.core.os.bundleOf

const val EXTRA_PICKER_SHOWING = "EXTRA_PICKER_SHOWING"
const val EXTRA_PICKER_ADDRESS = "EXTRA_PICKER_ADDRESS"

data class ImagePickerState(
        var isShowing: Boolean = false,
        var currentAddress: String = ""
) {

    companion object

}

val ImagePickerState.bundle: Bundle
    get() {
        val list = arrayOf(
                Pair(EXTRA_PICKER_SHOWING, isShowing),
                Pair(EXTRA_PICKER_ADDRESS, currentAddress)
        )
        return bundleOf(*list)
    }

fun ImagePickerState.Companion.fromBundle(data: Bundle?): ImagePickerState {
    return if (data != null) {
        ImagePickerState(
                isShowing = data.getBoolean(EXTRA_PICKER_SHOWING),
                currentAddress = data.getString(EXTRA_PICKER_ADDRESS, "")
        )
    } else {
        ImagePickerState()
    }
}

