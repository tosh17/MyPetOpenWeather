package ru.thstdio.mypetopenweather.usecase.repository

import ru.thstdio.mypetopenweather.data.Repository
import ru.thstdio.mypetopenweather.domain.PlaceAndWeather
import javax.inject.Inject

class GetWeatherPredictForCity @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(city: String): PlaceAndWeather =
        repository.getWeatherPredictForCity(city)
}