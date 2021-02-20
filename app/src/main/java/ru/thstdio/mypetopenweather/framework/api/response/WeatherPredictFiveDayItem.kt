package ru.thstdio.mypetopenweather.framework.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherPredictFiveDayItem(
    @SerialName("dt") val dateTime: Long,
    @SerialName("main") val main: MainResponse,
    @SerialName("weather") val weather: List<WeatherResponse>,
    @SerialName("wind") val wind: WindResponse,
    @SerialName("clouds") val clouds: CloudsResponse,
    @SerialName("dt_txt") val dateTimeText: String
)