package ru.thstdio.feature_map.impl.data

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.thstdio.core.domain.Location
import ru.thstdio.core.domain.Marker
import ru.thstdio.core.domain.Place
import ru.thstdio.core.domain.Weather
import ru.thstdio.core_db.impl.data.entity.PlaceDBO
import ru.thstdio.feature_map.impl.framework.api.response.WeatherPredictResponse
import ru.thstdio.mypetopenweather.framework.room.entity.WeatherDBO
import kotlin.math.roundToInt

fun android.location.Location.toDomainLocation(): Location {
    return Location(
        longitude = this.longitude,
        latitude = this.latitude
    )
}

fun Marker.toGoogleMarker(): MarkerOptions {
    val position = LatLng(this.location.latitude, this.location.longitude)
    return MarkerOptions()
        .position(position)
        .title(this.title)
        .snippet(this.snippet)
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
