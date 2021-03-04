package ru.thstdio.mypetopenweather.domain

data class PredictForFiveDay(
    val place: Place,
    val listWeather: List<WeatherWithDate>
)