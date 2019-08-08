package com.olamide.cowryconvert.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.olamide.cowryconvert.R
import com.olamide.cowryconvert.model.rx.Status
import com.olamide.cowryconvert.model.rx.VmResponse
import com.olamide.cowryconvert.viewmodel.MainViewModel
import timber.log.Timber

class MainActivity : BaseActivity() {


    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        mainViewModel.getMultipleData(listOf("BTC","ETH","XRP"), listOf("NGN","USD","EUR"))
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
}
