package com.olamide.cowryconvert

import android.content.Context
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import java.text.DateFormat

class CurrencyAsYDateAsXAxisLabelFormatter(context: Context, dateFormat: DateFormat, var currencyDisplay: String) :
    DateAsXAxisLabelFormatter(context, dateFormat) {

    override fun formatLabel(value: Double, isValueX: Boolean): String {
        return if (isValueX) {
            super.formatLabel(value, isValueX)
        } else {
            //format As currency
            currencyDisplay + " " + super.formatLabel(value, isValueX)
        }
    }
}