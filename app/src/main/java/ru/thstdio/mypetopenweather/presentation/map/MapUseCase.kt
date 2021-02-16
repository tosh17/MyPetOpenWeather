package ru.thstdio.mypetopenweather.presentation.map

import ru.thstdio.mypetopenweather.usecase.repository.*
import javax.inject.Inject

class MapUseCase @Inject constructor(
    val getPlaces: GetPlacesWithLoadedWeather
)