package ru.thstdio.feature_cities.impl.data.convertor

import android.annotation.SuppressLint
import ru.thstdio.core.domain.Location
import ru.thstdio.core.domain.Place
import ru.thstdio.core.domain.Weather
import ru.thstdio.core_db.impl.data.entity.PlaceDBO
import ru.thstdio.feature_cities.impl.framework.api.response.WeatherPredictResponse
import ru.thstdio.mypetopenweather.framework.room.entity.WeatherDBO
import kotlin.math.roundToInt

fun android.location.Location.toDomainLocation(): Location {
    return Location(
        longitude = this.longitude,
        latitude = this.latitude
    )
}


fun PlaceDBO.toPlace(): Place {
    return Place(
        cityId = this.id,
        name = this.name,
        location = Location(
            latitude = this.latitude,
            longitude = this.longitude
        )
    )
}

fun WeatherPredictResponse.toPlaceDBO(): PlaceDBO {
    return PlaceDBO(
        id = this.id,
        name = this.name,
        longitude = this.coord.lon,
        latitude = this.coord.lat,
        lastRequest = System.currentTimeMillis()
    )
}


fun WeatherPredictResponse.toWeatherDBO(isHourly: Boolean): WeatherDBO {
    return WeatherDBO(
        cityId = this.id,
        time = this.dt,
        isHourly = isHourly,
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
}

fun WeatherDBO.toWeather(): Weather {
    return Weather(
        temperature = this.temperature,
        feelsLike = this.feelsLike,
        iconId = this.iconId,
        tempMax = this.tempMax,
        tempMin = this.tempMin,
        humidity = this.humidity,
        pressure = this.pressure,
        windSpeed = this.windSpeed,
        wendDeg = this.wendDeg
    )
}

@SuppressLint("SimpleDateFormat")
private fun convertDate(time: Long): String {
    val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm")
    val date = java.util.Date(time * 1000)
    return sdf.format(date)
}