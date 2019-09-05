package com.itechart.turvotest.network

import com.itechart.turvotest.model.Ticker
import io.reactivex.Single

interface LoadTickerHistoryUseCase {
    fun load(tickers: Array<String>): Single<List<Ticker>>
}