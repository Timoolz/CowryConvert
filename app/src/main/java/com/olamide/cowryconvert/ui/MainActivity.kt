package com.olamide.cowryconvert.ui

import android.content.res.Resources
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.olamide.cowryconvert.R
import com.olamide.cowryconvert.model.Crypto
import com.olamide.cowryconvert.model.rx.Status
import com.olamide.cowryconvert.model.rx.VmResponse
import com.olamide.cowryconvert.viewmodel.MainViewModel
import timber.log.Timber
import java.io.BufferedReader
import java.io.File

class MainActivity : BaseActivity() {

    lateinit var cryptos: List<Crypto>
    lateinit var  currencies: List<String>
    lateinit var mainViewModel: MainViewModel
    val res: Resources = resources
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getCryptoList()
        currencies = res.getStringArray(R.array.currency).toMutableList()

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        mainViewModel.getMultipleData(cryptos.map { it.code }, currencies)
        mainViewModel.getMultipleResponse().observe(this, Observer<VmResponse> { response -> handleResponse(response) })

    }

    private fun handleResponse(vmResponse: VmResponse) {
        when (vmResponse.status) {
            Status.LOADING -> {
            }
            Status.SUCCESS ->

                try {

                    Timber.i(vmResponse.data.toString())


                } catch (e: Exception) {

                }

            Status.ERROR -> {


            }

            else -> {
            }
        }
    }

    fun getCryptoList() {
        val cryptosString: String = File("./src/main/res/raw/cryptos.json").readText(Charsets.UTF_8)
        cryptos = Gson().fromJson(cryptosString, Array<Crypto>::class.java).toMutableList()

    }
}
