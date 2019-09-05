package com.itechart.turvotest.network.alpha.model

import com.google.gson.annotations.SerializedName
import com.itechart.turvotest.model.Ticker
import timber.log.Timber
import java.util.*
import kotlin.random.Random

data class DailySchema(
    @SerializedName("Time Series (Daily)") val timeSeriesDaily: Map<Date, DayData>? = null,
    @SerializedName("Meta Data") val metaData: MetaData? = null
)

fun DailySchema.toTicker(): Ticker {
    val history = mutableMapOf<Date, Float>()
    timeSeriesDaily?.forEach {
        try {
            val price = it.value.close?.toFloat()
            history[it.key] = price ?: 0f
        } catch (e: NumberFormatException) {
            Timber.e(e)
        }
    }
    return Ticker(
        id = metaData?.symbol?.hashCode()?.toLong() ?: Random.nextLong(),
        history = history,
        lastPrice = history.values.max() ?: 0f,
        title = metaData?.symbol ?: "unknown ticker"
    )
}


data class MetaData(
    @SerializedName("1. Information") val info: String? = null,
    @SerializedName("2. Symbol") val symbol: String? = null,
    @SerializedName("3. Last Refreshed") val lastRefreshed: Date? = null,
    @SerializedName("4. Output Size") val outputSize: String? = null,
    @SerializedName("5. Time Zone") val timeZone: String? = null
)

data class DayData(
    @SerializedName("1. open") val open: String? = null,
    @SerializedName("2. high") val high: String? = null,
    @SerializedName("3. low") val low: String? = null,
    @SerializedName("4. close") val close: String? = null,
    @SerializedName("5. volume") val volume: String? = null
)