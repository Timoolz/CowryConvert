package com.olamide.cowryconvert.model

import com.google.gson.annotations.SerializedName

data class HistoryData(
    @SerializedName("time")
    var time: Long,
    @SerializedName("open")
    var open: Double,
    @SerializedName("high")
    var high: Double,
    @SerializedName("low")
    var low: Double,
    @SerializedName("close")
    var close: Double,
    @SerializedName("volumeFrom")
    var volumeFrom: Double,
    @SerializedName("volumeTo")
    var volumeTo: Double
)