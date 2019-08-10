package com.olamide.cowryconvert.ui

import android.content.res.Resources
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import com.olamide.cowryconvert.R
import com.olamide.cowryconvert.adapter.MainAdapter
import com.olamide.cowryconvert.model.CompareMultipleResponse
import com.olamide.cowryconvert.model.Crypto
import com.olamide.cowryconvert.model.rx.Status
import com.olamide.cowryconvert.model.rx.VmResponse
import com.olamide.cowryconvert.viewmodel.MainViewModel
import timber.log.Timber
import java.io.File
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter


class MainActivity : BaseActivity(), MainAdapter.MainAdapterOnClickListener {

    lateinit var cryptos: List<Crypto>
    lateinit var currencies: List<String>
    lateinit var currentCurrency: String
    lateinit var mainViewModel: MainViewModel

    lateinit var cryptoRv: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var mAdapter: MainAdapter

    lateinit var cryptMainData: CompareMultipleResponse


    lateinit var res: Resources
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        res = resources
        initDefaultDataConfig()
        bindUiComponents()
        initViewModel()

    }

    private fun bindUiComponents() {
        layoutManager = LinearLayoutManager(this)
        cryptoRv = findViewById(R.id.rv_crypto)
        cryptoRv.layoutManager = layoutManager
        cryptoRv.isNestedScrollingEnabled = false



    }

    private fun initDefaultDataConfig() {
        getCryptoList()
        currencies = res.getStringArray(R.array.currency).toMutableList()
        currentCurrency = currencies[0]
    }


    private fun handleResponse(vmResponse: VmResponse) {
        when (vmResponse.status) {
            Status.LOADING -> {
            }
            Status.SUCCESS ->

                try {

                    cryptMainData =
                        jacksonObjectMapper().convertValue(vmResponse.data, CompareMultipleResponse::class.java)
                    mAdapter = MainAdapter(this, cryptos, currentCurrency, this)
                    cryptoRv.adapter = mAdapter
                    mAdapter.setCryptoConversionData(cryptMainData, currentCurrency)


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

    override fun onClickListener(position: Int) {
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        mainViewModel.getMultipleResponse().observe(this, Observer<VmResponse> { response -> handleResponse(response) })
        getCryptoDetails()
    }

    fun getCryptoList() {
        val text = resources.openRawResource(R.raw.cryptos)
            .bufferedReader().use { it.readText() }
        //val cryptosString: String = File("cryptos.json").readText(Charsets.UTF_8)
        cryptos = Gson().fromJson(text, Array<Crypto>::class.java).toMutableList()

    }

    fun getCryptoDetails() {
        mainViewModel.getMultipleData(cryptos.map { it.code }, currencies)

    }
}
