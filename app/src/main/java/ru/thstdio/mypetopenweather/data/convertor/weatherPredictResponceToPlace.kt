package ru.thstdio.mypetopenweather.data.convertor

import ru.thstdio.core_db.impl.data.entity.PlaceDBO
import ru.thstdio.mypetopenweather.framework.api.response.WeatherPredictResponse

fun WeatherPredictResponse.toPlace(): PlaceDBO =
    PlaceDBO(
        id = this.id,
        name = this.name,
        longitude = this.coord.lon,
        latitude = this.coord.lat,
        lastRequest = System.currentTimeMillis()
    )