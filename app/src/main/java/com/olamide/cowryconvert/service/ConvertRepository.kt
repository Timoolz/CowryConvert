package com.olamide.cowryconvert.service

import com.olamide.cowryconvert.model.ConvertResponse
import io.reactivex.Observable

class ConvertRepository(var convertApi: ConvertApi) {


    fun getLatestRates(base: String, symbols: List<String>?): Observable<ConvertResponse> {
        return convertApi.getLatestRates(base, symbols?.joinToString { "," })
    }

    fun getDateRates(date: String, base: String, symbols: List<String>): Observable<ConvertResponse> {
        return convertApi.getDateRates(date, base, symbols.joinToString { "," })
    }

    fun getHistoricalRates(base: String, symbols: List<String>, startAt:String, endAt:String ): Observable<ConvertResponse> {
        return convertApi.getHistoricalRates(base, symbols.joinToString { "," },startAt,endAt)
    }

}
