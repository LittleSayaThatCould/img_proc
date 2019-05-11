package com.valer.sergey.imageprocessor.di.components

import com.valer.sergey.imageprocessor.app.App
import dagger.Component
import com.valer.sergey.imageprocessor.di.modules.AppModule
import com.valer.sergey.imageprocessor.di.modules.FragmentsModule
import com.valer.sergey.imageprocessor.di.modules.RepositoryModule
import com.valer.sergey.imageprocessor.di.scopes.AppScope
import com.valer.sergey.imageprocessor.domain.repository.ImageProcessingRepository
import com.valer.sergey.imageprocessor.presentation.activities.MainActivity
import com.valer.sergey.imageprocessor.presentation.fragments.ImageProcessingFragment
import com.valer.sergey.imageprocessor.presentation.presenters.ImageProcessingPresenter
import com.valer.sergey.imageprocessor.utils.ResourceManager

@Component(
        modules = [
            AppModule::class,
            FragmentsModule::class,
            RepositoryModule::class
        ]
)
@AppScope
interface AppComponent {
    fun inject(app: App)
    fun inject(mainActivity: MainActivity)
    fun inject(imageProcessingFragment: ImageProcessingFragment)
    fun inject(imageProcessingPresenter: ImageProcessingPresenter)

}