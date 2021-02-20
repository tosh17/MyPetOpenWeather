package ru.thstdio.mypetopenweather.presentation.city

import ru.thstdio.mypetopenweather.usecase.navigation.NavigateToMap
import ru.thstdio.mypetopenweather.usecase.navigation.NavigationToDetail
import ru.thstdio.mypetopenweather.usecase.repository.*
import javax.inject.Inject

class CityUseCase @Inject constructor(
    val getWeatherByCityName: GetWeatherPredictForCity,
    val getWeatherByPlace: GetWeatherPredictForPlace,
    val getWeatherByCoordinate: GetWeatherPredictForCoordinate,
    val getPlaces: GetPlaces,
    val setPlaceToTop: SetPlaceToTop,
    val navigateToMap: NavigateToMap,
    val navigateToDetail: NavigationToDetail
)