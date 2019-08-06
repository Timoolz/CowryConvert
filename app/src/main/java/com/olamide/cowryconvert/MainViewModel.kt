package com.olamide.cowryconvert

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
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


    public fun getLatest() {
        disposables.add(convertRepository.getLatestRates("USD", null)
            .subscribeOn(schedulersFactory.io())
            .observeOn(schedulersFactory.ui())
            .doOnSubscribe { loader -> mainLiveData.value = VmResponse.loading() }
            .subscribe(
                { result -> mainLiveData.value = (VmResponse.success(result)) },
                { throwable -> mainLiveData.value = (VmResponse.error(throwable)) }
            ))

    }

    public fun getLatestResponse() :MutableLiveData<VmResponse>{
        return mainLiveData
    }


    override fun onCleared() {
        disposables.clear()

    }
}