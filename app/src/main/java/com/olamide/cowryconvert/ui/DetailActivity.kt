package com.olamide.cowryconvert.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.olamide.cowryconvert.AppConstants
import com.olamide.cowryconvert.util.CurrencyAsYDateAsXAxisLabelFormatter
import com.olamide.cowryconvert.R
import com.olamide.cowryconvert.model.*
import com.olamide.cowryconvert.model.rx.Status
import com.olamide.cowryconvert.model.rx.VmResponse
import com.olamide.cowryconvert.model.viewfilter.ViewFrequency
import com.olamide.cowryconvert.model.viewfilter.ViewRange
import com.olamide.cowryconvert.viewmodel.DetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import timber.log.Timber
import java.text.SimpleDateFormat


class DetailActivity : BaseActivity() {

    lateinit var detailViewModel: DetailViewModel

    lateinit var currentCurrency: String
    lateinit var cryptoHistData: CompareHistoryResponse
    lateinit var rawDetails: CurrencyDetailsRaw
    lateinit var dispDetails: CurrencyDetailsDisp
    lateinit var currentCrypto: Crypto
    var viewBundle = Bundle()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initDefaultDataConfig()
        initViewModel(savedInstanceState)
        bindBaseUiComponents()

    }

    private fun initDefaultDataConfig() {
        currentCrypto = intent.getParcelableExtra("crypto")!!
        currentCurrency = intent.getStringExtra("currency")!!
        rawDetails = intent.getParcelableExtra("rawDet")!!
        dispDetails = intent.getParcelableExtra("displayDet")!!

    }

    private fun initViewModel(savedInstanceState: Bundle?) {
        detailViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
        detailViewModel.getViewBundle().observe(this, Observer<Bundle> { response ->
            viewBundle = response
            getHistoryDetails()

        })
        detailViewModel.getDetailResponse()
            .observe(this, Observer<VmResponse> { response -> handleResponse(response) })

        if (savedInstanceState == null) {
            viewBundle.putParcelable("range", ViewRange.WEEK)
            viewBundle.putParcelable("frequency", ViewFrequency.HOURLY)
            setBundleInVM()
            //getHistoryDetails()

        }
    }

    private fun bindBaseUiComponents() {

        Picasso.with(this)
            .load(AppConstants.COMPARE_BASE_URL + rawDetails.imageUrl)
            .fit()
            .into(iv_logo)

        tv_name.text = currentCrypto.name
        tv_code.text = "- " + currentCrypto.code
        tv_price.text = dispDetails.price
        tv_change.text = dispDetails.changePct24Hour + "%"
        if (rawDetails.changePct24Hour < 0) {
            tv_change.setTextColor(ContextCompat.getColor(this, R.color.red))
        } else {
            tv_change.setTextColor(ContextCompat.getColor(this, R.color.green))
        }

        tv_volume.text = dispDetails.volume
        tv_up.text = dispDetails.highDay
        tv_down.text = dispDetails.lowDay
        market.text = dispDetails.market
        market_2.text = dispDetails.lastMarket

        initClickHandlers()

    }


    fun getHistoryDetails() {
        detailViewModel.getDetailData(viewBundle, currentCrypto.code, currentCurrency)

    }


    private fun displayGraph() {

        // styling viewport
        //gvDetails.viewport.backgroundColor = Color.argb(255, 222, 222, 222)
        gvDetails.viewport.setDrawBorder(true)
        gvDetails.viewport.borderColor = ContextCompat.getColor(this, R.color.colorOnBackground)

        // styling grid/labels
        gvDetails.gridLabelRenderer.gridColor =
            ContextCompat.getColor(this, R.color.colorOnBackground)
        gvDetails.gridLabelRenderer.isHighlightZeroLines = true
        gvDetails.gridLabelRenderer.horizontalLabelsColor =
            ContextCompat.getColor(this, R.color.colorOnBackground)
        gvDetails.gridLabelRenderer.verticalLabelsColor =
            ContextCompat.getColor(this, R.color.colorOnBackground)
        gvDetails.gridLabelRenderer.verticalLabelsVAlign =
            GridLabelRenderer.VerticalLabelsVAlign.MID
        gvDetails.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.BOTH

        gvDetails.gridLabelRenderer.numVerticalLabels = 4
        //for Date Formatting
        gvDetails.gridLabelRenderer.setHumanRounding(true)
        gvDetails.gridLabelRenderer.numHorizontalLabels = 3

        if (ViewFrequency.DAILY == viewBundle.getParcelable("frequency")!!) {
            gvDetails.gridLabelRenderer.labelFormatter =
                CurrencyAsYDateAsXAxisLabelFormatter(
                    this,
                    SimpleDateFormat("dd-MMM"),
                    dispDetails.toSymbol
                )
        } else {
            gvDetails.gridLabelRenderer.labelFormatter =
                CurrencyAsYDateAsXAxisLabelFormatter(
                    this,
                    SimpleDateFormat("dd-MMM HH:mm"),
                    dispDetails.toSymbol
                )
        }


        // set manual x bounds to have nice steps
        gvDetails.viewport.setMinX(cryptoHistData.data.first().x)
        gvDetails.viewport.setMaxX(cryptoHistData.data.last().x)
        gvDetails.viewport.isXAxisBoundsManual = true


        // set manual y bounds to have nice steps
        val padY = (cryptoHistData.data.maxBy { it.y }!!.y * 0.01)
        gvDetails.viewport.setMinY(cryptoHistData.data.minBy { it.y }!!.y - padY)
        gvDetails.viewport.setMaxY(cryptoHistData.data.maxBy { it.y }!!.y + padY)
        gvDetails.viewport.isYAxisBoundsManual = true

        // enable scaling and scrolling
        gvDetails.viewport.isScalable = true
        gvDetails.viewport.isScrollable = true

        gvDetails.gridLabelRenderer.reloadStyles()

        // styling path
        val pathSeries = LineGraphSeries<DataPoint>(cryptoHistData.data.toTypedArray())
        pathSeries.color = ContextCompat.getColor(this, R.color.green)
        //pathSeries.isDrawBackground = true
        //pathSeries.backgroundColor = Color.argb(100, 255, 255, 0)
        //pathSeries.backgroundColor = ContextCompat.getColor(this, R.color.graph_path)
        pathSeries.isDrawDataPoints = true
        pathSeries.dataPointsRadius = 2.5f
        pathSeries.thickness = 3
        gvDetails.removeAllSeries()
        gvDetails.addSeries(pathSeries)

    }

    private fun modifyGraphParam() {
        val viewRange: ViewRange = viewBundle.getParcelable("range")!!
        val viewFrequency: ViewFrequency = viewBundle.getParcelable("frequency")!!

        when (viewRange) {
            ViewRange.DAY -> {
                btDaily.isEnabled = false
                btWeekly.isEnabled = true
                btMonthly.isEnabled = true

            }

            ViewRange.WEEK -> {
                btDaily.isEnabled = true
                btWeekly.isEnabled = false
                btMonthly.isEnabled = true
            }

            ViewRange.MONTH -> {
                btDaily.isEnabled = true
                btWeekly.isEnabled = true
                btMonthly.isEnabled = false
            }

        }
    }

    private fun handleResponse(vmResponse: VmResponse) {
        when (vmResponse.status) {
            Status.LOADING -> {
            }
            Status.SUCCESS ->

                try {

                    cryptoHistData =
                        jacksonObjectMapper().convertValue(
                            vmResponse.data,
                            CompareHistoryResponse::class.java
                        )
                    displayGraph()
                    modifyGraphParam()


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

    fun setBundleInVM() {
        detailViewModel.setViewBundle(viewBundle)
    }

    private fun initClickHandlers() {
        //launch info webview
        ivInfo.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra(
                "url",
                AppConstants.COMPARE_BASE_URL + "/coins/" + currentCrypto.code.toLowerCase() + "/overview/" + currentCurrency.toLowerCase()
            )
            startActivity(intent)
        }

        //Change graph Frequency
        sp_frequency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewBundle.putParcelable(
                    "frequency",
                    ViewFrequency.valueOf(parent!!.selectedItem.toString().toUpperCase())
                )
                setBundleInVM()
            }

        }

        //set Onclick listeners for range
        btDaily.setOnClickListener {
            viewBundle.putParcelable(
                "range",
                ViewRange.DAY
            )
            setBundleInVM()
        }

        btWeekly.setOnClickListener {
            viewBundle.putParcelable(
                "range",
                ViewRange.WEEK
            )
            setBundleInVM()
        }

        btMonthly.setOnClickListener {
            viewBundle.putParcelable(
                "range",
                ViewRange.MONTH
            )
            setBundleInVM()
        }


    }


}
