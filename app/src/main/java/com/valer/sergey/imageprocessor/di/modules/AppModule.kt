package com.valer.sergey.imageprocessor.di.modules


import dagger.Module
import dagger.Provides
import com.valer.sergey.imageprocessor.di.scopes.AppScope
import com.valer.sergey.imageprocessor.presentation.contracts.MainContract
import com.valer.sergey.imageprocessor.presentation.presenters.MainPresenter

@Module
class AppModule {

    @AppScope
    @Provides
    fun provideMainPresenter(): MainContract.Presenter = MainPresenter()

}