package com.valer.sergey.imageprocessor.data

import android.graphics.Bitmap

data class ImageProcessingState(
        var currentBitmap: Bitmap? = null,
        val processedItems: MutableList<Bitmap> = mutableListOf(),
        var loadAddress: String = "",
        var scrolledPosition: Int = 0
)