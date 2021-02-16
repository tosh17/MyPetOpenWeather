package ru.thstdio.mypetopenweather.presentation.city

import ru.thstdio.mypetopenweather.usecase.navigation.NavigateToMap
import ru.thstdio.mypetopenweather.usecase.repository.GetPlaces
import ru.thstdio.mypetopenweather.usecase.repository.GetWeatherPredictForCity
import ru.thstdio.mypetopenweather.usecase.repository.GetWeatherPredictForCoordinate
import ru.thstdio.mypetopenweather.usecase.repository.GetWeatherPredictForPlace
import javax.inject.Inject

class CityUseCase @Inject constructor(
    val getWeatherByCityName: GetWeatherPredictForCity,
    val getWeatherByPlace: GetWeatherPredictForPlace,
    val getWeatherByCoordinate: GetWeatherPredictForCoordinate,
    val getPlaces: GetPlaces,
    val navigateToMap : NavigateToMap
)