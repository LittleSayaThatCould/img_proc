package com.valer.sergey.imageprocessor.di.modules

import com.valer.sergey.imageprocessor.presentation.contract.ImageProcessingContract
import com.valer.sergey.imageprocessor.presentation.presenter.ImageProcessingPresenter
import dagger.Module
import dagger.Provides
import com.valer.sergey.imageprocessor.di.scopes.AppScope

@Module
class FragmentsModule {

    @AppScope
    @Provides
    fun provideImageProcessingPresenter(): ImageProcessingContract.Presenter = ImageProcessingPresenter()
}