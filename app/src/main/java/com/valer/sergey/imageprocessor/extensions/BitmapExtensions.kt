package com.valer.sergey.imageprocessor.extensions

import android.graphics.*

const val ROTATION_ANGLE = 90f
fun Bitmap.rotate(): Bitmap =
        Bitmap.createBitmap(this, 0, 0, width, height, Matrix().apply { postRotate(ROTATION_ANGLE) }, true)

fun Bitmap.mirror(): Bitmap =
        Bitmap.createBitmap(this, 0, 0, width, height, Matrix().apply { preScale(-1f, 1f) }, true)

fun Bitmap.toBlackAndWhite(): Bitmap {
    val colorMatrix = ColorMatrix().apply { setSaturation(0f) }
    val colorMatrixFilter = ColorMatrixColorFilter(colorMatrix)
    val blackAndWhiteBitmap = this.copy(Bitmap.Config.ARGB_8888, true)
    val paint = Paint().apply { colorFilter = colorMatrixFilter }
    val canvas = Canvas(blackAndWhiteBitmap).apply { drawBitmap(blackAndWhiteBitmap, 0f, 0f, paint) }
    return blackAndWhiteBitmap
}