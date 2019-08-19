package com.olamide.cowryconvert.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Crypto(
    var code: String,
    var name: String
):Parcelable