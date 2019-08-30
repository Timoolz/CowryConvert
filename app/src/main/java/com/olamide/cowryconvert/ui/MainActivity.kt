package com.olamide.cowryconvert.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import com.olamide.cowryconvert.R
import com.olamide.cowryconvert.adapter.MainAdapter
import com.olamide.cowryconvert.model.CompareMultipleResponse
import com.olamide.cowryconvert.model.Crypto
import com.olamide.cowryconvert.model.rx.Status
import com.olamide.cowryconvert.model.rx.VmResponse
import com.olamide.cowryconvert.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.crypto_card.view.*
import timber.log.Timber


class MainActivity : BaseActivity() {


    lateinit var cryptos: List<Crypto>
    lateinit var currencies: List<String>
    lateinit var currentCurrency: String
    lateinit var mainViewModel: MainViewModel

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mAdapter: MainAdapter

    lateinit var cryptMainData: CompareMultipleResponse


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDefaultDataConfig()
        bindUiComponents()
        initViewModel()

    }

    private fun bindUiComponents() {
        layoutManager = LinearLayoutManager(this)
        rv_crypto.layoutManager = layoutManager
        rv_crypto.isNestedScrollingEnabled = false


    }

    private fun initDefaultDataConfig() {
        getCryptoList()
        currencies = resources.getStringArray(R.array.currency).toMutableList()
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
                    mAdapter = MainAdapter(this, cryptos, currentCurrency, CardClicked(this, currentCurrency))
                    rv_crypto.adapter = mAdapter
                    populateAdapter()
                    sp_currency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }

                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            currentCurrency = sp_currency.selectedItem.toString()
                            populateAdapter()
                        }

                    }


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

    private fun populateAdapter() {
        mAdapter.setCryptoConversionData(cryptMainData, currentCurrency)
    }

    class CardClicked(val activity: BaseActivity, val currentCurrency: String) :
        MainAdapter.MainAdapterOnClickListener {
        override fun onClickListener(currentCrypto: Crypto, view: View, more: Boolean) {

            if (view.info_layout.visibility == View.GONE) {
                view.info_layout.visibility = View.VISIBLE
            } else if (!more) {
                view.info_layout.visibility = View.GONE
            } else {
                //var crypto = cryptos[position]
                var startDetailIntent = Intent(activity, DetailActivity::class.java)
                startDetailIntent.putExtra("currency", currentCurrency)
                startDetailIntent.putExtra("crypto", currentCrypto)
                activity.startActivity(startDetailIntent)
            }


        }
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
