package com.olamide.cowryconvert.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.olamide.cowryconvert.AppConstants
import com.olamide.cowryconvert.R
import com.olamide.cowryconvert.model.CompareHistoryResponse
import com.olamide.cowryconvert.model.Crypto
import com.olamide.cowryconvert.model.CurrencyDetailsDisp
import com.olamide.cowryconvert.model.CurrencyDetailsRaw
import com.olamide.cowryconvert.model.rx.Status
import com.olamide.cowryconvert.model.rx.VmResponse
import com.olamide.cowryconvert.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import timber.log.Timber

class DetailActivity : BaseActivity() {

    lateinit var detailViewModel: DetailViewModel

    lateinit var currentCurrency: String
    lateinit var cryptoHistData: CompareHistoryResponse
    lateinit var rawDetails: CurrencyDetailsRaw
    lateinit var dispDetails: CurrencyDetailsDisp
    lateinit var currentCrypto: Crypto
    var daily = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initDefaultDataConfig()
        initViewModel(savedInstanceState)
        bindBaseUiComponents()

    }

    private fun initDefaultDataConfig() {
        currentCrypto = intent.getParcelableExtra("crypto")
        currentCurrency = intent.getStringExtra("currency")!!
        rawDetails = intent.getParcelableExtra("rawDet")
        dispDetails = intent.getParcelableExtra("displayDet")

    }

    private fun initViewModel(savedInstanceState: Bundle?) {
        detailViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
        detailViewModel.getDaily().observe(this, Observer<Boolean> { response ->
            daily = response
            getHistoryDetails()

        })
        detailViewModel.getDetailResponse().observe(this, Observer<VmResponse> { response -> handleResponse(response) })

        if (savedInstanceState == null) {
            detailViewModel.setDaily(daily)
            //getHistoryDetails()

        }
    }

    private fun bindBaseUiComponents() {


        ivInfo.setOnClickListener {
            var intent = Intent(this, WebActivity::class.java)
            intent.putExtra(
                "url",
                AppConstants.COMPARE_BASE_URL + "/coins/" + currentCrypto.code.toLowerCase() + "/overview/" + currentCurrency.toLowerCase()
            )
            startActivity(intent)
        }
    }


    fun getHistoryDetails() {
        detailViewModel.getDetailData(daily, currentCrypto.code, currentCurrency)

    }

    private fun handleResponse(vmResponse: VmResponse) {
        when (vmResponse.status) {
            Status.LOADING -> {
            }
            Status.SUCCESS ->

                try {

                    cryptoHistData =
                        jacksonObjectMapper().convertValue(vmResponse.data, CompareHistoryResponse::class.java)


                } catch (e: Exception) {
                    Timber.e(e)

                }

            Status.ERROR -> {
                Timber.e(vmResponse.error)

            }

            else -> {
            }
        }
    }


}
