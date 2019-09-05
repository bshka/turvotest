package com.itechart.turvotest.network.alpha

import com.itechart.turvotest.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AlphaApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder().addQueryParameter("apikey", BuildConfig.ALPHA_API_KEY)
            .build()
        val request = originalRequest.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}