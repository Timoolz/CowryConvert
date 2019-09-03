package com.olamide.cowryconvert.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

import com.olamide.cowryconvert.AppConstants.Companion.COMPARE_BASE_URL
import com.olamide.cowryconvert.R
import com.olamide.cowryconvert.model.CompareMultipleResponse
import com.olamide.cowryconvert.model.Crypto
import com.olamide.cowryconvert.model.CurrencyDetailsDisp
import com.olamide.cowryconvert.model.CurrencyDetailsRaw
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.crypto_card.view.*
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




        fun populateCryptoItem(rawDetails: CurrencyDetailsRaw?, dispDetails: CurrencyDetailsDisp?, currentCrypto: Crypto) {

            Picasso.with(context)
                .load(COMPARE_BASE_URL + rawDetails!!.imageUrl)
                .fit()
                .into(itemView.iv_logo)

            itemView.tv_name.text = currentCrypto.name
            itemView.tv_code.text = "- " + currentCrypto.code
            itemView.tv_price.text = dispDetails!!.price
            itemView.tv_change.text = dispDetails!!.changePct24Hour + "%"
            if (rawDetails.changePct24Hour < 0) {
                itemView.tv_change.setTextColor(ContextCompat.getColor(context, R.color.red))
            } else {
                itemView.tv_change.setTextColor(ContextCompat.getColor(context, R.color.green))
            }

            itemView.tv_volume.text = dispDetails!!.volume
            itemView.tv_up.text = dispDetails!!.highDay
            itemView.tv_down.text = dispDetails!!.lowDay
            itemView.market.text = dispDetails!!.market
            itemView.market_2.text = dispDetails!!.lastMarket
            itemView.iv_more.setOnClickListener(this)
        }

        override fun onClick(v: View) {

//            var rawDetails:CurrencyDetailsRaw = cryptoData.raw.keys[adapterPosition]
//            var dispDetails: CurrencyDetailsDisp = cryptoData.display
            clickListener.onClickListener(cryptoList.find { it.code == ArrayList(cryptoData.raw.keys)[adapterPosition] }!!, itemView, v.id == itemView.iv_more.id)

        }
    }

    interface MainAdapterOnClickListener {
        fun onClickListener(currentCrypto: Crypto, view: View, more: Boolean)
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
        holder.populateCryptoItem(rawDetails, dispDetails, currentCrypto)


    }

    override fun getItemCount(): Int {
        return if (::cryptoData.isInitialized) {
            if (cryptoData.raw.size > 0) {
                cryptoData.raw.size
            } else {
                0
            }
        } else {
            if (cryptoList.isNotEmpty()) {
                cryptoList.size
            } else {
                0
            }
        }

    }


}