package ru.thstdio.mypetopenweather.framework.di.module

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.thstdio.mypetopenweather.framework.api.service.OpenWeatherApi
import javax.inject.Singleton

@Module
class ApiModule {
    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

        @Singleton
        @Provides
        fun provideApi(): OpenWeatherApi {
            val json = Json {
                ignoreUnknownKeys = true
            }
            val contentType = "application/json".toMediaType()
            val client = OkHttpClient().newBuilder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
            val retrofit: Retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()
            return retrofit.create(OpenWeatherApi::class.java)
        }
    }
}