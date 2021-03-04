package ru.thstdio.mypetopenweather.data.convertor

import ru.thstdio.core_db.impl.data.entity.PlaceDBO
import ru.thstdio.mypetopenweather.domain.Place

fun PlaceDBO.toPlace(): Place = Place(
    cityId = this.id,
    name = this.name,
    location = Location(
        latitude = this.latitude,
        longitude = this.longitude
    )
)