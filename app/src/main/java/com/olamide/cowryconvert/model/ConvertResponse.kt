package com.olamide.cowryconvert.model

import com.google.gson.annotations.SerializedName
import java.util.HashMap

 class ConvertResponse(


    @SerializedName("error")
    var error: String,
    @SerializedName("timestamp")
    var timestamp: Long? = null,
    @SerializedName("base")
    var base: String,
    @SerializedName("date")
    var date: String,
    @SerializedName("rates")
    var rates: HashMap<String, Float>,
    @SerializedName("symbols")
    var symbols: HashMap<String, String>
//    @SerializedName("result")
//    internal var result: Float? = null


)