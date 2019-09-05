package com.olamide.cowryconvert.model.viewfilter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class ViewRange(val range: String) : Parcelable {
    DAY("day"),
    WEEK("week"),
    MONTH("month")
}