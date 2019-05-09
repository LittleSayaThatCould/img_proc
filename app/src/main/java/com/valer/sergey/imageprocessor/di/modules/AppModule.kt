package com.valer.sergey.imageprocessor.di.modules

import com.valer.sergey.imageprocessor.presentation.contract.MainContract
import com.valer.sergey.imageprocessor.presentation.presenter.MainPresenter
import dagger.Module
import dagger.Provides
import com.valer.sergey.imageprocessor.di.scopes.AppScope

@Module
class AppModule {

    @AppScope
    @Provides
    fun provideMainPresenter(): MainContract.Presenter = MainPresenter()

}