package com.olamide.cowryconvert.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.olamide.cowryconvert.model.CompareHistoryResponse
import com.olamide.cowryconvert.model.CompareMultipleResponse
import com.olamide.cowryconvert.model.ConvertResponse
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

    fun getMultipleData(
        fromSymbol: String,
        toSymbol: String
    ): Observable<CompareHistoryResponse> {
        return convertApi.getDailyHistory(

            fromSymbol,
            toSymbol,
            10
        )
    }

}
