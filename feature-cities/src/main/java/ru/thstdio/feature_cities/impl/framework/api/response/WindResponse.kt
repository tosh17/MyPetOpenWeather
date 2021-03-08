package ru.thstdio.feature_cities.impl.framework.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WindResponse(
    @SerialName("speed") val speed: Double,
    @SerialName("deg") val deg: Int
)