package com.itechart.turvotest.network.alpha

import com.itechart.turvotest.network.alpha.model.DailySchema
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageApi {

    @GET("/query?function=TIME_SERIES_DAILY")
    fun symbolDaily(@Query("symbol") symbol: String): Single<DailySchema>

}