package com.olamide.cowryconvert.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import com.olamide.cowryconvert.UiUtils
import com.olamide.cowryconvert.viewmodel.factory.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {


    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory
    @Inject
    protected lateinit var uiUtils: UiUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!uiUtils.isDarkTheme() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

    }


}