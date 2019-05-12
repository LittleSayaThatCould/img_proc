package com.valer.sergey.imageprocessor.extensions

import android.graphics.*

const val ROTATION_ANGLE = 90f
const val BREAK_IMG_HEIGHT = 640
const val BREAK_IMG_WIDTH = 480


fun Bitmap.rotate(): Bitmap =
        Bitmap.createBitmap(this, 0, 0, width, height, Matrix().apply { postRotate(ROTATION_ANGLE) }, true)

fun Bitmap.mirror(): Bitmap =
        Bitmap.createBitmap(this, 0, 0, width, height, Matrix().apply { preScale(-1f, 1f) }, true)

fun Bitmap.toBlackAndWhite(): Bitmap {
    val colorMatrix = ColorMatrix().apply { setSaturation(0f) }
    val colorMatrixFilter = ColorMatrixColorFilter(colorMatrix)
    val blackAndWhiteBitmap = this.copy(Bitmap.Config.RGB_565, true)
    val paint = Paint().apply { colorFilter = colorMatrixFilter }
    val canvas = Canvas(blackAndWhiteBitmap).apply { drawBitmap(blackAndWhiteBitmap, 0f, 0f, paint) }
    return blackAndWhiteBitmap
}

fun Bitmap.createScaledBitmap(): Bitmap =
        if (this.width > BREAK_IMG_WIDTH || this.height > BREAK_IMG_HEIGHT) {
            val scale = BREAK_IMG_WIDTH.toFloat() / this.width.toFloat()
            Bitmap.createScaledBitmap(this, (this.width * scale).toInt(), (this.height * scale).toInt(), false)
        } else {
            this
        }
