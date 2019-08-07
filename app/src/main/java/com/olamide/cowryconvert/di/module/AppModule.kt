package com.olamide.cowryconvert.di.module

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.olamide.cowryconvert.service.ConvertApi
import com.olamide.cowryconvert.service.ConvertRepository
import com.olamide.cowryconvert.viewmodel.factory.ViewModelFactory
import com.olamide.cowryconvert.di.rx.SchedulersFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: Application) {
    @Provides
    @Singleton
    fun provideApplication(): Application = application


    @Provides
    @Singleton
    internal fun providesRepository(convertApi: ConvertApi): ConvertRepository {
        return ConvertRepository(convertApi)
    }

    @Provides
    @Singleton
    internal fun providesViewModelFactory(
        convertRepository: ConvertRepository,
        schedulersFactory: SchedulersFactory
    ): ViewModelProvider.Factory {
        return ViewModelFactory(
            application,
            convertRepository,
            schedulersFactory
        )
    }
}