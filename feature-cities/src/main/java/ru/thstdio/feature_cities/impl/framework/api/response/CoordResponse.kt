package ru.thstdio.feature_cities.impl.framework.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoordResponse(
    @SerialName("lon") val lon: Double,
    @SerialName("lat") val lat: Double
)