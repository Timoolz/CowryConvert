package com.olamide.cowryconvert.model

import com.google.gson.annotations.SerializedName

data class CompareHistoryResponse(
    @SerializedName("Response")
    var response: String?,
    @SerializedName("Message")
    var message: String?,
    @SerializedName("TimeTo")
    var timeTo: Long,
    @SerializedName("TimeFrom")
    var timeFrom:Long,
    @SerializedName("Data")
    var data:List<HistoryData>
)