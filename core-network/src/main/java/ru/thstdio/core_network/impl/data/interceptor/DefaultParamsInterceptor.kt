package ru.thstdio.core_network.impl.data.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.thstdio.core_network.impl.data.service.Configuration

private const val QueryParameterLang = "lang"
private const val QueryParameterUnits = "units"
private const val QueryParameterAppId = "appid"

class DefaultParamsInterceptor(private val config: Configuration) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl: HttpUrl = originalRequest.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(QueryParameterLang, config.language.value)
            .addQueryParameter(QueryParameterUnits, config.units.value)
            .addQueryParameter(QueryParameterAppId, config.appId)
            .build()
        val requestBuilder = originalRequest.newBuilder()
            .url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}
