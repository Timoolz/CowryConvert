package com.olamide.cowryconvert.di.module

import com.olamide.cowryconvert.BaseActivity
import com.olamide.cowryconvert.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuilderModule {

    @ContributesAndroidInjector
    abstract fun bindBaseActivity(): BaseActivity

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}