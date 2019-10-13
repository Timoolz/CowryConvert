package com.olamide.cowryconvert.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.olamide.cowryconvert.util.TempUtils
import com.olamide.cowryconvert.util.UiUtils
import com.olamide.cowryconvert.viewmodel.factory.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {


    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory
    @Inject
    protected lateinit var uiUtils: UiUtils
    @Inject
    protected lateinit var tempUtils: TempUtils
    val mapper = jacksonObjectMapper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapper.registerKotlinModule()

        if (!uiUtils.isDarkTheme() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

    }


    fun showToast(message: String){
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }



}