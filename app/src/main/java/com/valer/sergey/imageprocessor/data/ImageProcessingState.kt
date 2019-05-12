package com.valer.sergey.imageprocessor.data

import android.graphics.Bitmap

data class ImageProcessingState(
        var currentBitmap: Bitmap? = null,
        val processedItems: MutableList<Bitmap> = mutableListOf(),
        var loadAddress: String = "https://www.gipsyteam.ru/upload/Titleimage/fixed160x120/5/5/5597.jpg",
        var scrolledPosition: Int = 0
)