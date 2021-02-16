package ru.thstdio.mypetopenweather.data.convertor

import ru.thstdio.mypetopenweather.framework.api.response.WeatherPredictResponse
import ru.thstdio.mypetopenweather.framework.room.entity.PlaceDBO

fun WeatherPredictResponse.toPlace(): PlaceDBO =
    PlaceDBO(
        id = this.id,
        name = this.name,
        longitude = this.coord.lon,
        latitude = this.coord.lat,
        lastRequest = System.currentTimeMillis()
    )