package ru.thstdio.mypetopenweather.domain

data class Place(
    val name: String,
    val cityId: Long,
    val location: Location
)