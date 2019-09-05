package com.olamide.cowryconvert.service

import android.os.Bundle
import com.olamide.cowryconvert.model.*
import com.olamide.cowryconvert.model.viewfilter.ViewFrequency
import com.olamide.cowryconvert.model.viewfilter.ViewRange
import io.reactivex.Observable

class ConvertRepository(var convertApi: ConvertApi) {


    fun getLatestRates(base: String, symbols: List<String>?): Observable<ConvertResponse> {
        return convertApi.getLatestRates("https://api.exchangeratesapi.io/", base, symbols?.joinToString { "," })
    }

    fun getDateRates(date: String, base: String, symbols: List<String>): Observable<ConvertResponse> {
        return convertApi.getDateRates("https://api.exchangeratesapi.io/", date, base, symbols.joinToString { "," })
    }

    fun getHistoricalRates(
        base: String,
        symbols: List<String>,
        startAt: String,
        endAt: String
    ): Observable<ConvertResponse> {
        return convertApi.getHistoricalRates(
            "https://api.exchangeratesapi.io/",
            base,
            symbols.joinToString { "," },
            startAt,
            endAt
        )
    }

    fun getMultipleData(
        fromSymbols: List<String>,
        toSymbols: List<String>
    ): Observable<CompareMultipleResponse> {

        return convertApi.getMultipleData(
            fromSymbols.joinToString().replace("\\s".toRegex(), ""),
            toSymbols.joinToString().replace("\\s".toRegex(), "")
        )
    }

    fun getHistoryData(
        bundle: Bundle,
        fromSymbol: String,
        toSymbol: String
    ): Observable<CompareHistoryResponse> {
        val viewRange: ViewRange = bundle.getParcelable("range")!!
        val viewFrequency: ViewFrequency = bundle.getParcelable("frequency")!!
        val url: String
        val limit: Int

        when (viewFrequency) {
            ViewFrequency.HOURLY -> {
                url = "histohour"
                limit = when (viewRange) {
                    ViewRange.DAY -> {
                        24
                    }
                    ViewRange.WEEK -> {
                        168
                    }
                    ViewRange.MONTH -> {
                        744
                    }
                }
            }
            ViewFrequency.DAILY -> {
                url = "histoday"
                limit = when (viewRange) {
                    ViewRange.DAY -> {
                        1
                    }
                    ViewRange.WEEK -> {
                        7
                    }
                    ViewRange.MONTH -> {
                        31
                    }
                }
            }
        }

        return convertApi.getHistoryData(
            url,
            fromSymbol,
            toSymbol,
            limit
        )
    }

}
