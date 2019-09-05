package com.itechart.turvotest.network.tradier.model

import com.itechart.turvotest.model.Ticker
import timber.log.Timber
import java.util.*
import kotlin.random.Random

data class DailySchema(
	val history: History? = null
)

data class DayItem(
	val date: Date? = null,
	val volume: Int? = null,
	val high: Double? = null,
	val low: Double? = null,
	val close: Double? = null,
	val open: Double? = null
)

data class History(
	val day: List<DayItem?>? = null
)

fun DailySchema.toTicker(title: String): Ticker {
	val data = mutableMapOf<Date, Float>()
	history?.day?.forEach {
		try {
			data[it!!.date!!] = it.close!!.toFloat()
		} catch (e: NullPointerException) {
			Timber.e(e)
		}
	}
	return Ticker(
		id = Random.nextLong(),
		history = data,
		lastPrice = data.values.max() ?: 0f,
		title = title
	)
}

