package com.valer.sergey.imageprocessor.di.modules

import com.valer.sergey.imageprocessor.di.scopes.AppScope
import com.valer.sergey.imageprocessor.repository.ImageProcessingRepository
import com.valer.sergey.imageprocessor.repository.ImageProcessingRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @AppScope
    @Provides
    fun provideImageProcessingRepository(): ImageProcessingRepository = ImageProcessingRepositoryImpl()

}