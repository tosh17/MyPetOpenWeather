package ru.thstdio.core.domain

data class PredictForFiveDay(
    val place: Place,
    val listWeather: List<WeatherWithDate>
)