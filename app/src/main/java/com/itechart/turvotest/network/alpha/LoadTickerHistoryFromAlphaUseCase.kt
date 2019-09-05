package com.itechart.turvotest.network.alpha

import com.itechart.turvotest.common.SchedulersProvider
import com.itechart.turvotest.model.Ticker
import com.itechart.turvotest.network.LoadTickerHistoryUseCase
import com.itechart.turvotest.network.alpha.model.toTicker
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class LoadTickerHistoryFromAlphaUseCase(
    private val api: AlphaVantageApi,
    private val schedulersProvider: SchedulersProvider
): LoadTickerHistoryUseCase {

    private fun load(ticker: String): Single<Ticker> =
        api.symbolDaily(ticker)
            .subscribeOn(schedulersProvider.io())
            .map { it.toTicker() }

    override fun load(tickers: Array<String>): Single<List<Ticker>> {
        var observable: Single<MutableList<Ticker>> = Single.just(mutableListOf())
        tickers.forEach { symbol ->
            observable = observable.zipWith(
                load(symbol), BiFunction { list, ticker ->
                    list.apply {
                        add(ticker)
                    }
                }
            )
        }
        return observable.map { it.toList() }
    }

}