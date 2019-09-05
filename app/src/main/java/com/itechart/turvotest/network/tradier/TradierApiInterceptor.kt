package com.itechart.turvotest.network.tradier

import com.itechart.turvotest.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class TradierApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().addHeader("Accept", "application/json")
            .addHeader("Authorization", "Bearer ${BuildConfig.TRADIER_API_KEY}")
            .build()
        return chain.proceed(request)
    }
}