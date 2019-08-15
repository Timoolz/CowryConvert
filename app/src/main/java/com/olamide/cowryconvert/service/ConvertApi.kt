package com.olamide.cowryconvert.service

import com.olamide.cowryconvert.model.CompareHistoryResponse
import com.olamide.cowryconvert.model.CompareMultipleResponse
import com.olamide.cowryconvert.model.ConvertResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ConvertApi {

    @GET("latest")
    abstract fun getLatestRates(
        @Url url: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String?
    )
            : Observable<ConvertResponse>


    @GET("{date}")
    abstract fun getDateRates(
        @Url url: String,
        @Path(value = "date", encoded = true) date: String?,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    )
            : Observable<ConvertResponse>

    @GET("history")
    abstract fun getHistoricalRates(
        @Url url: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String?,
        @Query("start_at") startAt: String,
        @Query("end_at") endAt: String
    )
            : Observable<ConvertResponse>

    @GET("pricemultifull")
    abstract fun getMultipleData(
        @Query("fsyms") base: String,
        @Query("tsyms") symbols: String
    )
            : Observable<CompareMultipleResponse>


    @GET("{path}")
    abstract fun getHistoryData(
        @Path(value = "path", encoded = true) path: String,
        @Query("fsym") base: String,
        @Query("tsym") symbols: String,
        @Query("limit") limit: Int
    )
            : Observable<CompareHistoryResponse>


}
