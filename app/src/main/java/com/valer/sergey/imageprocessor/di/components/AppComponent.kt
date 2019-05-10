package com.valer.sergey.imageprocessor.di.components

import com.valer.sergey.imageprocessor.app.App
import dagger.Component
import com.valer.sergey.imageprocessor.di.modules.AppModule
import com.valer.sergey.imageprocessor.di.modules.FragmentsModule
import com.valer.sergey.imageprocessor.di.scopes.AppScope
import com.valer.sergey.imageprocessor.presentation.activities.MainActivity
import com.valer.sergey.imageprocessor.presentation.fragments.ImageProcessingFragment

@Component(
        modules = [
            AppModule::class,
            FragmentsModule::class
        ]
)
@AppScope
interface AppComponent {
    fun inject(app: App)
    fun inject(mainActivity: MainActivity)
    fun inject(imageProcessingFragment: ImageProcessingFragment)

}