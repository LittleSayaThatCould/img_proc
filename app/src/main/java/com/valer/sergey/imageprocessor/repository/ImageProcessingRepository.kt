package com.valer.sergey.imageprocessor.repository

import io.reactivex.Single
import okhttp3.ResponseBody

interface ImageProcessingRepository {
    fun loadImage(address: String): Single<ResponseBody>
}