package ru.thstdio.core.domain

data class Weather(
    val temperature: Int,
    val feelsLike: Int,
    val iconId: Int,
    val tempMin: Int,
    val tempMax: Int,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double,
    val wendDeg: Int
)