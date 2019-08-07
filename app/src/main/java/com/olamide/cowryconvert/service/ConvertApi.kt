package com.olamide.cowryconvert.service

import com.olamide.cowryconvert.model.ConvertResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ConvertApi {

    @GET("latest")
    abstract fun getLatestRates(
        @Query("base") base: String,
        @Query("symbols") symbols: String?
    )
            : Observable<ConvertResponse>


    @GET("{date}")
    abstract fun getDateRates(
        @Path(value = "date", encoded = true) date: String?,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    )
            : Observable<ConvertResponse>

    @GET("history")
    abstract fun getHistoricalRates(
        @Query("base") base: String,
        @Query("symbols") symbols: String?,
        @Query("start_at") startAt: String,
        @Query("end_at") endAt: String
    )
            : Observable<ConvertResponse>


}
