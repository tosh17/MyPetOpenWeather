package ru.thstdio.mypetopenweather.framework.api.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class DefaultParamsInterceptor (private val config: Configuration) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl: HttpUrl = originalRequest.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("lang", config.language.value)
            .addQueryParameter("units", config.units.value)
            .addQueryParameter("appid", config.appId)
            .build()
        val requestBuilder = originalRequest.newBuilder()
            .url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }

    data class Configuration(val appId: String, val units: UnitsType, val language: Language) {
        enum class Language(val value: String) {
            Russian("ru"), English("en")
        }

        enum class UnitsType(val value: String) {
            Standard("standard"), Metric("metric"), Imperial("imperial")
        }
    }
}
