package ru.thstdio.mypetopenweather.data.convertor

import ru.thstdio.mypetopenweather.domain.Weather
import ru.thstdio.mypetopenweather.framework.api.response.WeatherPredictResponse

fun WeatherPredictResponse.toWeather(): Weather =
    Weather(
        temperature = this.main.temp,
        feelsLike = this.main.feelsLike,
        iconId = this.weather.firstOrNull()?.id ?: 0,
        tempMax = this.main.tempMax,
        tempMin = this.main.tempMin,
        humidity = this.main.humidity,
        pressure = this.main.pressure
    )
