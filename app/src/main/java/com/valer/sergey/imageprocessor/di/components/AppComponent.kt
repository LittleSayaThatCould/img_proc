package com.valer.sergey.imageprocessor.di.components

import com.valer.sergey.imageprocessor.presentation.activity.MainActivity
import com.valer.sergey.imageprocessor.app.App
import com.valer.sergey.imageprocessor.presentation.fragment.ImageProcessingFragment
import dagger.Component
import com.valer.sergey.imageprocessor.di.modules.AppModule
import com.valer.sergey.imageprocessor.di.modules.FragmentsModule
import com.valer.sergey.imageprocessor.di.scopes.AppScope

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