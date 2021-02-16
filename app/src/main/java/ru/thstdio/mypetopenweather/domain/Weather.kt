package ru.thstdio.mypetopenweather.domain

data class Weather(
    val temperature: Double,
    val feelsLike: Double,
    val iconId:Int,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int
)