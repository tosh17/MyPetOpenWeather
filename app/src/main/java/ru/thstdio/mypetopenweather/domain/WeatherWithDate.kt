package ru.thstdio.mypetopenweather.domain

data class WeatherWithDate(
    val date: Long,
    val dateTitle: String,
    val weather: Weather
)