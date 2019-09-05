package com.itechart.turvotest.common

import com.google.gson.Gson
import com.itechart.turvotest.BuildConfig
import com.itechart.turvotest.network.LoadTickerHistoryUseCase
import com.itechart.turvotest.network.alpha.AlphaApiInterceptor
import com.itechart.turvotest.network.alpha.AlphaVantageApi
import com.itechart.turvotest.network.tradier.LoadTickerHistoryFromTradierUseCase
import com.itechart.turvotest.network.tradier.TradierApi
import com.itechart.turvotest.network.tradier.TradierApiInterceptor
import com.itechart.turvotest.screens.portfolio.PortfolioViewModel
import com.itechart.turvotest.screens.tickers.TickersViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

val viewModels = module {
    viewModel { TickersViewModel(androidContext()) }
    viewModel { (tickers: Array<String>) -> PortfolioViewModel(tickers, get(), get()) }
}

val rxJava = module {
    single { SchedulersProvider() }
}

val network = module {

    single {

        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val httpCacheDirectory = File(androidContext().cacheDir, "http-cache")
        val cache = Cache(httpCacheDirectory, cacheSize)

        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        val httpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(AlphaApiInterceptor())
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl("https://www.alphavantage.co/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AlphaVantageApi::class.java)

    }



    single {

        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val httpCacheDirectory = File(androidContext().cacheDir, "http-cache")
        val cache = Cache(httpCacheDirectory, cacheSize)

        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.HEADERS
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        val httpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(TradierApiInterceptor())
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl("https://sandbox.tradier.com")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TradierApi::class.java)

    }

    single<LoadTickerHistoryUseCase> { LoadTickerHistoryFromTradierUseCase(get(), get()) }

}