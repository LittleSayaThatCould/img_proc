package com.valer.sergey.imageprocessor.repository

import com.valer.sergey.imageprocessor.network.ImageService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ImageProcessingRepositoryImpl : ImageProcessingRepository {

    private val imageService: ImageService

    init {
        imageService = ImageService.instance
    }

    override fun loadImage(address: String) =
            imageService.loadImage(
                    url = address
            ).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())


}