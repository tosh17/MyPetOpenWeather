package ru.thstdio.mypetopenweather.data.convertor

import ru.thstdio.mypetopenweather.domain.Weather
import ru.thstdio.mypetopenweather.domain.WeatherWithDate
import ru.thstdio.mypetopenweather.framework.api.response.WeatherPredictFiveDayItem
import kotlin.math.roundToInt

fun WeatherPredictFiveDayItem.toWeatherWithDate(): WeatherWithDate {
    return WeatherWithDate(
        date = this.dateTime,
        dateTitle = this.dateTimeText,
        weather = Weather(
            temperature = this.main.temp.roundToInt(),
            feelsLike = this.main.feelsLike.roundToInt(),
            iconId = this.weather.firstOrNull()?.id ?: 0,
            tempMax = this.main.tempMax.roundToInt(),
            tempMin = this.main.tempMin.roundToInt(),
            humidity = this.main.humidity,
            pressure = this.main.pressure,
            windSpeed = this.wind.speed,
            wendDeg = this.wind.deg
        )
    )
}