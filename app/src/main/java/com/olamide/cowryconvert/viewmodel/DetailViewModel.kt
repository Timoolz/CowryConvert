package com.olamide.cowryconvert.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.olamide.cowryconvert.di.rx.SchedulersFactory
import com.olamide.cowryconvert.model.Crypto
import com.olamide.cowryconvert.model.CurrencyDetailsDisp
import com.olamide.cowryconvert.model.CurrencyDetailsRaw
import com.olamide.cowryconvert.model.rx.VmResponse
import com.olamide.cowryconvert.service.ConvertRepository
import io.reactivex.disposables.CompositeDisposable

class DetailViewModel(application: Application) : AndroidViewModel(application) {


    private val disposables = CompositeDisposable()
    private val detailLiveData = MutableLiveData<VmResponse>()

    private val viewBundleLive = MutableLiveData<Bundle>()

    private lateinit var convertRepository: ConvertRepository
    private lateinit var schedulersFactory: SchedulersFactory

    constructor(
        application: Application,
        convertRepository: ConvertRepository,
        schedulersFactory: SchedulersFactory
    ) : this(application) {
        this.convertRepository = convertRepository
        this.schedulersFactory = schedulersFactory
    }


    public fun getDetailData(bundle: Bundle, fromSymbol: String, toSymbol: String) {
        disposables.add(convertRepository.getHistoryData(bundle, fromSymbol, toSymbol)
            .subscribeOn(schedulersFactory.io())
            .observeOn(schedulersFactory.ui())
            .doOnSubscribe { loader -> detailLiveData.value = VmResponse.loading() }
            .subscribe(
                { result -> detailLiveData.value = (VmResponse.success(result)) },
                { throwable -> detailLiveData.value = (VmResponse.error(throwable)) }
            ))

    }

    public fun getDetailResponse(): MutableLiveData<VmResponse> {
        return detailLiveData
    }


    public fun setViewBundle(viewBundle: Bundle) {
        viewBundleLive.value = viewBundle
    }

    public fun getViewBundle(): LiveData<Bundle> {
        return viewBundleLive
    }


    override fun onCleared() {
        disposables.clear()

    }
}