package com.olamide.cowryconvert.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class CompareHistoryResponse(
    @SerializedName("Response")
    @JsonProperty("Response")
    var response: String?,
    @SerializedName("Message")
    @JsonProperty("Message")
    var message: String?,
    @SerializedName("TimeTo")
    @JsonProperty("TimeTo")
    var timeTo: Long,
    @SerializedName("TimeFrom")
    @JsonProperty("TimeFrom")
    var timeFrom:Long,
    @SerializedName("Data")
    @JsonProperty("Data")
    var data:List<HistoryData>
)