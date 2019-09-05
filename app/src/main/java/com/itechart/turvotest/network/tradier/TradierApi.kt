package com.itechart.turvotest.network.tradier

import com.itechart.turvotest.network.tradier.model.DailySchema
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TradierApi {

    @GET("/v1/markets/history?interval=daily")
    fun symbolDaily(
        @Query("symbol") symbol: String,
        @Query("start") start: String,
        @Query("end") end: String
    ): Single<DailySchema>

}