package com.itechart.turvotest.common

import com.itechart.turvotest.screens.tickers.TickersViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { TickersViewModel(androidContext()) }
}