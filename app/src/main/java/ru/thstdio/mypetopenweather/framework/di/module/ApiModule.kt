package ru.thstdio.mypetopenweather.framework.di.module

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import ru.thstdio.mypetopenweather.BuildConfig
import ru.thstdio.mypetopenweather.framework.api.interceptor.DefaultParamsInterceptor
import ru.thstdio.mypetopenweather.framework.api.service.Configuration
import ru.thstdio.mypetopenweather.framework.api.service.OpenWeatherApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideClient(interceptor: DefaultParamsInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    fun provideParamInterceptor(): DefaultParamsInterceptor {
        return DefaultParamsInterceptor(
            config = Configuration(
                language = Configuration.Language.Russian,
                units = Configuration.UnitsType.Metric,
                appId = BuildConfig.OPEN_WEATHER_API_ID
            )
        )
    }

    @ExperimentalSerializationApi
    @Provides
    fun provideJsonConverterFactory(): Converter.Factory {
        val json = Json {
            ignoreUnknownKeys = true
        }
        val contentType = "application/json".toMediaType()
        return json.asConverterFactory(contentType)
    }

    @Singleton
    @Provides
    fun provideApi(client: OkHttpClient, converter: Converter.Factory): OpenWeatherApi {
        val retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.OPEN_WEATHER_BASE_URL)
            .addConverterFactory(converter)
            .build()
        return retrofit.create(OpenWeatherApi::class.java)
    }
}