package com.olamide.cowryconvert.util

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.olamide.cowryconvert.model.NightMode
import javax.inject.Inject

class UiUtils @Inject constructor(val application: Application) {
    @Inject
    lateinit var tempUtils: TempUtils

    fun isDarkTheme(): Boolean {

        var nightMode = tempUtils.getNightMode()
        when (nightMode) {
            NightMode.DARK -> {
                return true
            }

            NightMode.LIGHT -> {
                return false
            }

            NightMode.SYSTEM -> {
                val mode =
                    application.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)
                if (mode == Configuration.UI_MODE_NIGHT_YES) {
                    return true
                }
                return false
            }
        }


    }

    fun setDarkTheme( nightMode: NightMode){
        tempUtils.putNightMode(nightMode)
        when (nightMode){
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

    }

}