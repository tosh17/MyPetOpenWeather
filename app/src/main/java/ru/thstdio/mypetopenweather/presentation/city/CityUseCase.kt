package ru.thstdio.mypetopenweather.presentation.city

import ru.thstdio.mypetopenweather.usecase.GetPlaces
import ru.thstdio.mypetopenweather.usecase.GetWeatherPredictForCity
import ru.thstdio.mypetopenweather.usecase.GetWeatherPredictForPlace
import javax.inject.Inject

class CityUseCase @Inject constructor(
    val getWeatherByCityName: GetWeatherPredictForCity,
    val getWeatherByPlace: GetWeatherPredictForPlace,
    val getPlaces: GetPlaces
)