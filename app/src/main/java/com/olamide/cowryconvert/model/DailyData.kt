package com.olamide.cowryconvert.model

import com.google.gson.annotations.SerializedName

data class DailyData(
    @SerializedName("time")
    var time: Long,
    @SerializedName("open")
    var open: Double,
    @SerializedName("high")
    var high: Double,
    @SerializedName("low")
    var low: Double,
    @SerializedName("close")
    var close: Double
)