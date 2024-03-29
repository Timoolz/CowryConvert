package com.olamide.cowryconvert.model.viewfilter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class ViewFrequency(val range: String) : Parcelable {
    HOURLY("hourly"),
    DAILY("daily"),
}