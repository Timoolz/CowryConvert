package com.olamide.cowryconvert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import timber.log.Timber

class MainActivity : BaseActivity() {


    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        mainViewModel.getLatest()
        mainViewModel.getLatestResponse().observe(this, Observer<VmResponse> { response -> loadUi(response) })


    }

    private fun loadUi(vmResponse: VmResponse) {
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
