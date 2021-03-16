package ru.thstdio.feature_cities.impl.usecase

import ru.thstdio.core.domain.Place
import ru.thstdio.core.domain.Weather
import ru.thstdio.feature_cities.impl.data.Repository
import javax.inject.Inject

class GetWeatherPredictForPlace @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(place: Place): Weather = repository.getWeatherPredictForPlace(place)
}