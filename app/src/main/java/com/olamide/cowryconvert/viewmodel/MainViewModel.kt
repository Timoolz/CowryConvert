package com.olamide.cowryconvert.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.olamide.cowryconvert.service.ConvertRepository
import com.olamide.cowryconvert.model.rx.VmResponse
import com.olamide.cowryconvert.di.rx.SchedulersFactory
import io.reactivex.disposables.CompositeDisposable

class MainViewModel : AndroidViewModel {

    private val disposables = CompositeDisposable()
    private val mainLiveData = MutableLiveData<VmResponse>()
    private val convertRepository: ConvertRepository
    private val schedulersFactory: SchedulersFactory

    constructor(
        application: Application,
        convertRepository: ConvertRepository,
        schedulersFactory: SchedulersFactory
    ) : super(application) {
        this.convertRepository = convertRepository
        this.schedulersFactory = schedulersFactory
    }


    public fun getMultipleData(fromSymbols:List<String>, toSymbols: List<String>) {
        disposables.add(convertRepository.getMultipleData(fromSymbols, toSymbols )
            .subscribeOn(schedulersFactory.io())
            .observeOn(schedulersFactory.ui())
            .doOnSubscribe { loader -> mainLiveData.value = VmResponse.loading() }
            .subscribe(
                { result -> mainLiveData.value = (VmResponse.success(result)) },
                { throwable -> mainLiveData.value = (VmResponse.error(throwable)) }
            ))

    }

    public fun getMultipleResponse() :MutableLiveData<VmResponse>{
        return mainLiveData
    }


    override fun onCleared() {
        disposables.clear()

    }
}