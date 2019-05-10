package com.valer.sergey.imageprocessor.di.modules

import dagger.Module
import dagger.Provides
import com.valer.sergey.imageprocessor.di.scopes.AppScope
import com.valer.sergey.imageprocessor.presentation.contracts.ImageProcessingContract
import com.valer.sergey.imageprocessor.presentation.presenters.ImageProcessingPresenter

@Module
class FragmentsModule {

    @AppScope
    @Provides
    fun provideImageProcessingPresenter(): ImageProcessingContract.Presenter = ImageProcessingPresenter()
}