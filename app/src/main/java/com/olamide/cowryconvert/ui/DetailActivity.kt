package com.olamide.cowryconvert.ui

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.olamide.cowryconvert.AppConstants
import com.olamide.cowryconvert.R
import com.olamide.cowryconvert.model.CompareHistoryResponse
import com.olamide.cowryconvert.model.Crypto
import com.olamide.cowryconvert.model.rx.Status
import com.olamide.cowryconvert.model.rx.VmResponse
import com.olamide.cowryconvert.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.crypto_card.view.*
import timber.log.Timber

class DetailActivity : BaseActivity() {

    lateinit var currentCurrency: String
    lateinit var detailViewModel: DetailViewModel
    lateinit var cryptoHistData: CompareHistoryResponse
    lateinit var currentCrypto: Crypto
    var daily = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initDefaultDataConfig()
        initViewModel()
        hideWebView()
        bindUiComponents()

    }

    private fun initDefaultDataConfig() {
        currentCrypto = intent.getParcelableExtra("crypto")
        currentCurrency = intent.getStringExtra("currency")
    }

    private fun handleResponse(vmResponse: VmResponse) {
        when (vmResponse.status) {
            Status.LOADING -> {
            }
            Status.SUCCESS ->

                try {

                    cryptoHistData =
                        jacksonObjectMapper().convertValue(vmResponse.data, CompareHistoryResponse::class.java)
                    bindUiComponents()


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

    private fun bindUiComponents() {
        ivInfo.setOnClickListener {
            webViewInfo.loadUrl(AppConstants.COMPARE_BASE_URL + "/coins/" + currentCrypto.code.toLowerCase() + "/overview/" + currentCurrency.toLowerCase())
            showWebView()

        }
    }

    private fun showWebView() {
        webViewInfo.visibility = View.VISIBLE
        ivInfo.visibility = View.INVISIBLE
        webViewInfo.settings.javaScriptEnabled = true
        webViewInfo.webViewClient = WebViewClient()
    }

    private fun hideWebView() {
        webViewInfo.visibility = View.INVISIBLE
        ivInfo.visibility = View.VISIBLE
    }

    private fun initViewModel() {
        detailViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
        detailViewModel.getDetailResponse().observe(this, Observer<VmResponse> { response -> handleResponse(response) })
        getHistoryDetails()
    }

    fun getHistoryDetails() {
        detailViewModel.getDetailData(daily, currentCrypto.code, currentCurrency)

    }
}
