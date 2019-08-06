package com.olamide.cowryconvert

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.fragment.app.Fragment
import com.olamide.cowryconvert.AppConstants.Companion.CONVERT_BASE_URL
import com.olamide.cowryconvert.di.component.DaggerAppComponent
import com.olamide.cowryconvert.di.module.ApiModule
import com.olamide.cowryconvert.di.module.AppModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

class ConvertApplication : Application(), HasActivityInjector,
    HasSupportFragmentInjector, HasServiceInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .apiModule(ApiModule(CONVERT_BASE_URL))
            .build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun serviceInjector(): AndroidInjector<Service> = serviceInjector
}