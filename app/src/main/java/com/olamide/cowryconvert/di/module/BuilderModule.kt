package com.olamide.cowryconvert.di.module

import com.olamide.cowryconvert.ui.BaseActivity
import com.olamide.cowryconvert.ui.DetailActivity
import com.olamide.cowryconvert.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuilderModule {

    @ContributesAndroidInjector
    abstract fun bindBaseActivity(): BaseActivity

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindDetailActivity(): DetailActivity
}