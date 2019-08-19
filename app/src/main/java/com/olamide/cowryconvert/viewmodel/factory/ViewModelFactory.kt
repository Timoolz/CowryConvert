package com.olamide.cowryconvert.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.olamide.cowryconvert.service.ConvertRepository
import com.olamide.cowryconvert.di.rx.SchedulersFactory
import com.olamide.cowryconvert.viewmodel.DetailViewModel
import com.olamide.cowryconvert.viewmodel.MainViewModel
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
            return MainViewModel(
                application,
                convertRepository,
                schedulersFactory
            ) as T
        }

        if (modelClass.isAssignableFrom(DetailViewModel::class.java!!)) {
            return DetailViewModel(
                application,
                convertRepository,
                schedulersFactory
            ) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}
