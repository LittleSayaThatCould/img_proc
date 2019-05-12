package com.valer.sergey.imageprocessor.repository

import com.valer.sergey.imageprocessor.network.ImageService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class ImageProcessingRepositoryImpl : ImageProcessingRepository {

    private val imageService: ImageService

    init {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://www.test.ru")
                .build()
        imageService = retrofit.create(ImageService::class.java)
    }

    override fun loadImage(address: String) =
            imageService.loadImage(
                    url = address
            ).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())


}