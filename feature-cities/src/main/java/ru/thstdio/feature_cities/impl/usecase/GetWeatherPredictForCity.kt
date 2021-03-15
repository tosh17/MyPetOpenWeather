package ru.thstdio.feature_cities.impl.usecase

import ru.thstdio.core.domain.PlaceAndWeather
import ru.thstdio.feature_cities.impl.data.Repository
import javax.inject.Inject

class GetWeatherPredictForCity @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(city: String): PlaceAndWeather =
        repository.getWeatherPredictForCity(city)
}