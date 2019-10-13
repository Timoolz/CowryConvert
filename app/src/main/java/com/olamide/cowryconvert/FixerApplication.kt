package com.olamide.cowryconvert

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.olamide.cowryconvert.AppConstants.Companion.COMPARE_API_BASE_URL
import com.olamide.cowryconvert.di.component.DaggerAppComponent
import com.olamide.cowryconvert.di.module.ApiModule
import com.olamide.cowryconvert.di.module.AppModule
import com.olamide.cowryconvert.model.NightMode
import com.olamide.cowryconvert.util.TempUtils
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


        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        var nightMode = NightMode.fromInt(
            preferences.getInt(
                AppConstants.PREF_NIGHT_MODE,
                TempUtils.DEFAULT_INT
            )
        )

        when (nightMode) {
            NightMode.DARK -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            NightMode.LIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            NightMode.SYSTEM -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }



        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .apiModule(ApiModule(COMPARE_API_BASE_URL))
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