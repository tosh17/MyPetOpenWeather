package ru.thstdio.mypetopenweather.framework.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoordResponse (
	@SerialName("lon") val lon : Double,
	@SerialName("lat") val lat : Double
)