package com.valer.sergey.imageprocessor.app

import android.app.Application
import com.valer.sergey.imageprocessor.di.ComponentsHolder
import com.valer.sergey.imageprocessor.di.components.DaggerAppComponent
import com.valer.sergey.imageprocessor.di.modules.AppModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        components.appComponent = DaggerAppComponent.builder()
                .appModule(AppModule()).build()
        components.appComponent.inject(this)
    }

    companion object {
        val components = ComponentsHolder
    }
}