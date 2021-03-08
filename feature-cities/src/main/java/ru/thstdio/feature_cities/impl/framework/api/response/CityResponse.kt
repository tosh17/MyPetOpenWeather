package ru.thstdio.feature_cities.impl.framework.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityResponse(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("coord") val coord: CoordResponse,
    @SerialName("sunrise") val sunrise: Int,
    @SerialName("sunset") val sunset: Int
)

