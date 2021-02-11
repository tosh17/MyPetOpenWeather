package ru.thstdio.mypetopenweather.data.convertor

import ru.thstdio.mypetopenweather.domain.Place
import ru.thstdio.mypetopenweather.framework.bd.entity.PlaceEntity

fun PlaceEntity.toPlace():Place = Place(
    cityId = this.id,
    name = this.name,
    latitude = this.latitude,
    longitude = this.longitude
)