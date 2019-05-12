package com.valer.sergey.imageprocessor.network

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface ImageService {

    @GET
    fun loadImage(@Url url: String): Single<ResponseBody>

}