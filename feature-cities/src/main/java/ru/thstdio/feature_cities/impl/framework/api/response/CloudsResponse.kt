package ru.thstdio.feature_cities.impl.framework.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CloudsResponse(
    @SerialName("all") val сloudiness: Int
)