package com.olamide.cowryconvert.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

import com.olamide.cowryconvert.AppConstants.Companion.COMPARE_IMAGE_BASE_URL
import com.olamide.cowryconvert.R
import com.olamide.cowryconvert.model.CompareMultipleResponse
import com.olamide.cowryconvert.model.Crypto
import com.olamide.cowryconvert.model.CurrencyDetailsDisp
import com.olamide.cowryconvert.model.CurrencyDetailsRaw
import com.squareup.picasso.Picasso
import java.util.ArrayList

class MainAdapter(
    val context: Context,
    var cryptoList: List<Crypto>,
    var currency: String,
    val clickListener: MainAdapterOnClickListener
) : RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder>() {

    lateinit var cryptoData: CompareMultipleResponse


    inner class MainAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {


        init {

            //ButterKnife.bind(this, view)
            view.setOnClickListener(this)
        }

        var ivLogo = view.findViewById<ImageView>(R.id.iv_logo)
        var tvName = view.findViewById<TextView>(R.id.tv_name)
        var tvCode = view.findViewById<TextView>(R.id.tv_code)
        var tvPrice = view.findViewById<TextView>(R.id.tv_price)
        var tvChange = view.findViewById<TextView>(R.id.tv_change)
        var tvVolume = view.findViewById<TextView>(R.id.tv_volume)
        var tvUp = view.findViewById<TextView>(R.id.tv_up)
        var tvDown = view.findViewById<TextView>(R.id.tv_down)
        var tvMarket = view.findViewById<TextView>(R.id.market)


        fun cryptoItem(rawDetails: CurrencyDetailsRaw?, dispDetails: CurrencyDetailsDisp?, currentCrypto: Crypto) {

            Picasso.with(context)
                .load(COMPARE_IMAGE_BASE_URL + rawDetails!!.imageUrl)
                .fit()
                .into(ivLogo)

            tvName.text = currentCrypto.name
            tvCode.text = "-" + currentCrypto.code
            tvPrice.text = dispDetails!!.price
            tvChange.text = dispDetails!!.changePct24Hour + "%"
            if (rawDetails.changePct24Hour < 0) {
                tvChange.setTextColor(ContextCompat.getColor(context, R.color.red))
            } else {
                tvChange.setTextColor(ContextCompat.getColor(context, R.color.green))
            }

            tvVolume.text = dispDetails!!.volume
            tvUp.text = dispDetails!!.highDay
            tvDown.text = dispDetails!!.lowDay
            tvMarket.text = dispDetails!!.lastMarket
        }

        override fun onClick(v: View) {
            val adapterPosition = adapterPosition
            clickListener.onClickListener(adapterPosition)
        }
    }

    interface MainAdapterOnClickListener {
        fun onClickListener(position: Int)
    }

    fun setCryptoConversionData(cryptoData: CompareMultipleResponse, currentCurrency: String) {
        this.cryptoData = cryptoData
        this.currency = currentCurrency
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.crypto_card, parent, false)

        return MainAdapterViewHolder(view)
    }


    override fun onBindViewHolder(holder: MainAdapterViewHolder, position: Int) {
        var currentCrypto: Crypto = cryptoList.find { it.code == ArrayList(cryptoData.raw.keys)[position] }!!
        var rawDetails = cryptoData.raw[currentCrypto.code]?.get(currency)
        var dispDetails = cryptoData.display[currentCrypto.code]?.get(currency)
        holder.cryptoItem(rawDetails, dispDetails, currentCrypto)
    }

    override fun getItemCount(): Int {
        return if (cryptoData.raw.size > 0) {
            cryptoData.raw.size
        } else {
            0
        }
    }


}