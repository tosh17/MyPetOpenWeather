package ru.thstdio.feature_map.impl.presentation


import ru.thstdio.feature_map.impl.usecase.GetPlacesWithLoadedWeather
import javax.inject.Inject

class MapUseCase @Inject constructor(
    val getPlaces: GetPlacesWithLoadedWeather
)