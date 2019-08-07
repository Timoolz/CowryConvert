package com.olamide.cowryconvert.model

import com.google.gson.annotations.SerializedName
import java.util.HashMap

data class HistoryResponse(
    @SerializedName("error")
    var error: String,
    @SerializedName("timestamp")
    var timestamp: Long? = null,
    @SerializedName("base")
    var base: String,
    @SerializedName("date")
    var date: String,
    @SerializedName("rates")
    var rates: List<HashMap<String, Float>>,
    @SerializedName("symbols")
    var symbols: HashMap<String, String>
)