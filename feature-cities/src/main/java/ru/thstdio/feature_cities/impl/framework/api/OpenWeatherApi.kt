package ru.thstdio.feature_cities.impl.framework.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.thstdio.feature_cities.impl.framework.api.response.WeatherPredictResponse

interface OpenWeatherApi {
    @GET("weather/")
    suspend fun getWeatherByCityName(@Query("q") city: String): WeatherPredictResponse

    @GET("weather/")
    suspend fun getWeatherByCityId(@Query("id") cityId: Long): WeatherPredictResponse

    @GET("weather/")
    suspend fun getWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): WeatherPredictResponse

}