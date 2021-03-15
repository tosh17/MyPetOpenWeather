package ru.thstdio.mypetopenweather.framework.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class WeatherPredictFiveDayRespose(
    @SerialName("list") val weathers: List<WeatherPredictFiveDayItem>,
    @SerialName("city") val city: CityResponse
)


