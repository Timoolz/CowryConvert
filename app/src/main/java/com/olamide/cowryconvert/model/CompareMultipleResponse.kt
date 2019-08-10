package com.olamide.cowryconvert.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class CompareMultipleResponse(
    @SerializedName("RAW")
    @JsonProperty("RAW")
    var raw: HashMap<String, LinkedHashMap<String, CurrencyDetailsRaw>>,
    @SerializedName("DISPLAY")
    @JsonProperty("DISPLAY")
    var display: HashMap<String, LinkedHashMap<String, CurrencyDetailsDisp>>,
    @SerializedName("Response")
    @JsonProperty("Response")
    var response: String?,
    @SerializedName("Message")
    @JsonProperty("Message")
    var message : String?
)