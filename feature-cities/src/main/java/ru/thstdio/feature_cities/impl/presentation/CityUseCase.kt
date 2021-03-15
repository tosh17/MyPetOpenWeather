package ru.thstdio.feature_cities.impl.presentation

import ru.thstdio.feature_cities.impl.usecase.*
import javax.inject.Inject

class CityUseCase @Inject constructor(
    val getWeatherByCityName: GetWeatherPredictForCity,
    val getWeatherByPlace: GetWeatherPredictForPlace,
    val getWeatherByCoordinate: GetWeatherPredictForCoordinate,
    val getPlaces: GetPlaces,
    val setPlaceToTop: SetPlaceToTop
)