package com.olamide.cowryconvert.di.component

import com.olamide.cowryconvert.ConvertApplication
import com.olamide.cowryconvert.di.module.ApiModule
import com.olamide.cowryconvert.di.module.AppModule
import com.olamide.cowryconvert.di.module.BuilderModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
    modules = [AndroidInjectionModule::class, AppModule::class, BuilderModule::class
        , ApiModule::class]
)
@Singleton
interface AppComponent {
    fun inject(application: ConvertApplication)
}