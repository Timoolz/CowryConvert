package com.olamide.cowryconvert.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrencyDetailsRaw(
    @SerializedName("FROMSYMBOL")
    @JsonProperty("FROMSYMBOL")
    var fromSymbol: String,
    @SerializedName("TOSYMBOL")
    @JsonProperty("TOSYMBOL")
    var toSymbol: String,
    @SerializedName("PRICE")
    @JsonProperty("PRICE")
    var price: Double,
    @SerializedName("LASTUPDATE")
    @JsonProperty("LASTUPDATE")
    var lastUpdate: Long,
    @SerializedName("OPENDAY")
    @JsonProperty("OPENDAY")
    var openDay: Double,
    @SerializedName("HIGHDAY")
    @JsonProperty("HIGHDAY")
    var highDay: Double,
    @SerializedName("LOWDAY")
    @JsonProperty("LOWDAY")
    var lowDay: Double,
    @SerializedName("OPENHOUR")
    @JsonProperty("OPENHOUR")
    var openHour: Double,
    @SerializedName("HIGHHOUR")
    @JsonProperty("HIGHHOUR")
    var highOur: Double,
    @SerializedName("LOWHOUR")
    @JsonProperty("LOWHOUR")
    var lowHour: Double,
    @SerializedName("CHANGE24HOUR")
    @JsonProperty("CHANGE24HOUR")
    var change24Hour: Double,
    @SerializedName("CHANGEPCT24HOUR")
    @JsonProperty("CHANGEPCT24HOUR")
    var changePct24Hour: Double,
    @SerializedName("CHANGEDAY")
    @JsonProperty("CHANGEDAY")
    var changeDay: Double,
    @SerializedName("CHANGEPCTDAY")
    @JsonProperty("CHANGEPCTDAY")
    var changePctDay: Double,
    @SerializedName("LASTMARKET")
    @JsonProperty("LASTMARKET")
    var lastMarket: String,
    @SerializedName("MARKET")
    @JsonProperty("MARKET")
    var market: String,
    @SerializedName("MKTCAP")
    @JsonProperty("MKTCAP")
    var volume: Double,
    @SerializedName("IMAGEURL")
    @JsonProperty("IMAGEURL")
    var imageUrl: String
):Parcelable
