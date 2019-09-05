package com.itechart.turvotest.network.tradier

import com.itechart.turvotest.common.SchedulersProvider
import com.itechart.turvotest.model.Ticker
import com.itechart.turvotest.network.LoadTickerHistoryUseCase
import com.itechart.turvotest.network.tradier.model.toTicker
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.text.SimpleDateFormat
import java.util.*

class LoadTickerHistoryFromTradierUseCase(
    private val api: TradierApi,
    private val schedulersProvider: SchedulersProvider
) : LoadTickerHistoryUseCase {

    private fun load(ticker: String, start: String, end: String): Single<Ticker> {
        return api.symbolDaily(
            symbol = ticker,
            start = start,
            end = end
        ).subscribeOn(schedulersProvider.io())
            .map { it.toTicker(ticker) }
    }

    override fun load(tickers: Array<String>): Single<List<Ticker>> {
        val start = DATE_FORMAT.format(Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, -10)
        }.time)
        val end = DATE_FORMAT.format(Calendar.getInstance().time)
        var observable: Single<MutableList<Ticker>> = Single.just(mutableListOf())
        tickers.forEach { symbol ->
            observable = observable.zipWith(
                load(symbol, start, end), BiFunction { list, ticker ->
                    list.apply {
                        add(ticker)
                    }
                }
            )
        }
        return observable.map { data ->
            data.filter { !it.history.isNullOrEmpty() }.toList()
        }
    }

    companion object {
        private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    }

}