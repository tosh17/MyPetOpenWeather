package ru.thstdio.mypetopenweather.data.convertor

import ru.thstdio.mypetopenweather.domain.Location
import ru.thstdio.mypetopenweather.domain.Place
import ru.thstdio.mypetopenweather.framework.room.entity.PlaceDBO

fun PlaceDBO.toPlace(): Place = Place(
    cityId = this.id,
    name = this.name,
    location = Location(
        latitude = this.latitude,
        longitude = this.longitude
    )
)