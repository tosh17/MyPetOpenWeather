package ru.thstdio.mypetopenweather.framework.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SysResponse (
	@SerialName("country") val country : String,
	@SerialName("sunrise") val sunrise : Int,
	@SerialName("sunset") val sunset : Int
)