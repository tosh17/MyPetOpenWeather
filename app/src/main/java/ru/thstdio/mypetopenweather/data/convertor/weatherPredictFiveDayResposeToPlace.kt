package ru.thstdio.mypetopenweather.data.convertor

import ru.thstdio.mypetopenweather.domain.Location
import ru.thstdio.mypetopenweather.domain.Place
import ru.thstdio.mypetopenweather.framework.api.response.WeatherPredictFiveDayRespose

fun WeatherPredictFiveDayRespose.toPlace(): Place {
    return Place(
        name = this.city.name,
        cityId = this.city.id,
        location = Location(
            latitude = this.city.coord.lat,
            longitude = this.city.coord.lon
        )
    )
}