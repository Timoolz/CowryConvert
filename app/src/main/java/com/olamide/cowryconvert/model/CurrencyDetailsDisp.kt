package com.olamide.cowryconvert.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class CurrencyDetailsDisp(
    @SerializedName("FROMSYMBOL")
    @JsonProperty("FROMSYMBOL")
    var fromSymbol: String,
    @SerializedName("TOSYMBOL")
    @JsonProperty("TOSYMBOL")
    var toSymbol: String,
    @SerializedName("PRICE")
    @JsonProperty("PRICE")
    var price: String,
    @SerializedName("LASTUPDATE")
    @JsonProperty("LASTUPDATE")
    var lastUpdate: String,
    @SerializedName("OPENDAY")
    @JsonProperty("OPENDAY")
    var openDay: String,
    @SerializedName("HIGHDAY")
    @JsonProperty("HIGHDAY")
    var highDay: String,
    @SerializedName("LOWDAY")
    @JsonProperty("LOWDAY")
    var lowDay: String,
    @SerializedName("OPENHOUR")
    @JsonProperty("OPENHOUR")
    var openHour: String,
    @SerializedName("HIGHHOUR")
    @JsonProperty("HIGHHOUR")
    var highOur: String,
    @SerializedName("LOWHOUR")
    @JsonProperty("LOWHOUR")
    var lowHour: String,
    @SerializedName("CHANGE24HOUR")
    @JsonProperty("CHANGE24HOUR")
    var change24Hour: String,
    @SerializedName("CHANGEPCT24HOUR")
    @JsonProperty("CHANGEPCT24HOUR")
    var changePct24Hour: String,
    @SerializedName("CHANGEDAY")
    @JsonProperty("CHANGEDAY")
    var changeDay: String,
    @SerializedName("CHANGEPCTDAY")
    @JsonProperty("CHANGEPCTDAY")
    var changePctDay: String,
    @SerializedName("IMAGEURL")
    @JsonProperty("IMAGEURL")
    var imageUrl: String
)
