package com.valer.sergey.imageprocessor.network

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface ImageService {

    @GET
    fun loadImage(@Url url: String): Single<ResponseBody>

    companion object {
        val instance: ImageService by lazy {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://www.test.ru")
                    .build()
            retrofit.create(ImageService::class.java)
        }
    }

}