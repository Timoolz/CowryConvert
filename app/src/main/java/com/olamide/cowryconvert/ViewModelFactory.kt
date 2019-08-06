package com.olamide.cowryconvert

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.olamide.cowryconvert.di.rx.SchedulersFactory
import javax.inject.Inject


class ViewModelFactory

@Inject
constructor(
    private val application: Application,
    private val convertRepository: ConvertRepository,
    private val schedulersFactory: SchedulersFactory
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java!!)) {
            return MainViewModel(application, convertRepository, schedulersFactory) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}
