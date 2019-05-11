package com.valer.sergey.imageprocessor.di.modules


import android.app.Application
import dagger.Module
import dagger.Provides
import com.valer.sergey.imageprocessor.di.scopes.AppScope
import com.valer.sergey.imageprocessor.presentation.contracts.MainContract
import com.valer.sergey.imageprocessor.presentation.presenters.MainPresenter
import com.valer.sergey.imageprocessor.utils.ResourceManager
import com.valer.sergey.imageprocessor.utils.ResourceManagerImpl

@Module
class AppModule(private val app: Application) {

    @AppScope
    @Provides
    fun provideMainPresenter(): MainContract.Presenter = MainPresenter()

    @AppScope
    @Provides
    fun provideResourceManager(): ResourceManager = ResourceManagerImpl(app.applicationContext)

}