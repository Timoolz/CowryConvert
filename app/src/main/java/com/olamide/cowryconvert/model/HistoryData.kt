package com.olamide.cowryconvert.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import com.jjoe64.graphview.series.DataPoint
import java.util.*

data class HistoryData(
    @SerializedName("time")
    @JsonProperty("time")
    var time: Long,
    @SerializedName("open")
    @JsonProperty("open")
    var open: Double,
    @SerializedName("high")
    @JsonProperty("high")
    var high: Double,
    @SerializedName("low")
    @JsonProperty("low")
    var low: Double,
    @SerializedName("close")
    @JsonProperty("close")
    var close: Double,
    @SerializedName("volumefrom")
    @JsonProperty("volumefrom")
    var volumeFrom: Double,
    @SerializedName("volumeto")
    @JsonProperty("volumeto")
    var volumeTo: Double
): DataPoint(Date(time*1000),open){
//   override fun getX(): Double {
//
//      return Date(time*1000).time.toDouble()
//   }
//
//   override fun getY(): Double {
//      return open
//   }
}