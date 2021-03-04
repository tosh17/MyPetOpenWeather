package ru.thstdio.mypetopenweather.framework.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherPredictResponse(
    @SerialName("coord") val coord: CoordResponse,
    @SerialName("weather") val weather: List<WeatherResponse>,
    @SerialName("base") val base: String,
    @SerialName("main") val main: MainResponse,
    @SerialName("visibility") val visibility: Int,
    @SerialName("wind") val wind: WindResponse,
    @SerialName("clouds") val clouds: CloudsResponse,
    @SerialName("dt") val dt: Long,
    @SerialName("sys") val sys: SysResponse,
    @SerialName("timezone") val timezone: Int,
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("cod") val cod: Int
)