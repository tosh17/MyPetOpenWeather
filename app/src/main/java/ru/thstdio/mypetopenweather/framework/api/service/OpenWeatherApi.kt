package ru.thstdio.mypetopenweather.framework.api.service

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.thstdio.mypetopenweather.framework.api.response.WeatherPredictResponse

private const val API_KEY = "cf0dce18363e74035728406179ef77b9"

interface OpenWeatherApi {
    @Headers("Content-type: application/json")
    @GET("weather/")
    suspend fun getWeatherByCityName(
        @Query("q") city: String,
        @Query("lang") language: String = "ru",
        @Query("units") units: String = "metric",
        @Query("appid") api: String = API_KEY,
    ): WeatherPredictResponse

    @Headers("Content-type: application/json")
    @GET("weather/")
    suspend fun getWeatherByCityId(
        @Query("id") cityId: Long,
        @Query("lang") language: String = "ru",
        @Query("units") units: String = "metric",
        @Query("appid") api: String = API_KEY,
    ): WeatherPredictResponse
}