package com.olamide.cowryconvert.util

import android.app.Application
import androidx.preference.PreferenceManager
import com.fasterxml.jackson.databind.ObjectMapper
import com.olamide.cowryconvert.AppConstants
import com.olamide.cowryconvert.model.NightMode
import javax.inject.Inject


class TempUtils @Inject constructor(val application: Application, val mapper: ObjectMapper) {

    companion object {

        const val DEFAULT_INT = 0
        const val DEFAULT_BOOLEAN = false
        const val DEFAULT_STRING = ""
    }

    internal var tempInt = 0
    internal var tempBoolean = false
    internal var tempString: String = ""


    fun readSharedPreferenceNumber( key: String): Int {
        val preferences = PreferenceManager.getDefaultSharedPreferences(application)
        tempInt = preferences.getInt(key, DEFAULT_INT)
        return tempInt
    }

    fun readSharedPreferenceBool( key: String): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(application)
        tempBoolean = preferences.getBoolean(key, DEFAULT_BOOLEAN)
        return tempBoolean
    }

    fun readSharedPreferenceString(key: String): String {
        val preferences = PreferenceManager.getDefaultSharedPreferences(application)
        tempString = preferences.getString(key, DEFAULT_STRING)!!
        return tempString
    }

    fun writeSharedPreferenceString(key: String, value: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(application)
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun writeSharedPreferenceBool(key: String, value: Boolean) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(application)
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun writeSharedPreferenceNumber(key: String, value: Int) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(application)
        val editor = preferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun removeSharedPreference(key: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(application)
        val editor = preferences.edit()
        editor.remove(key)
        editor.apply()
    }

    fun putNightMode(nightMode: NightMode){
        writeSharedPreferenceNumber(AppConstants.PREF_NIGHT_MODE, nightMode.value)
    }

    fun getNightMode(): NightMode {
        return NightMode.fromInt(readSharedPreferenceNumber(AppConstants.PREF_NIGHT_MODE))!!
    }


}