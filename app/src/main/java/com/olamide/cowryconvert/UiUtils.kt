package com.olamide.cowryconvert

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import javax.inject.Inject

class UiUtils @Inject constructor(val application: Application) {

    fun isDarkTheme(): Boolean {
        val mode = application.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)
        if (mode == Configuration.UI_MODE_NIGHT_YES) {
            return true
        }
        return false

    }

}