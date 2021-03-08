package ru.thstdio.feature_cities.impl.usecse

import ru.thstdio.core.domain.Location
import ru.thstdio.core.domain.PlaceAndWeather
import ru.thstdio.feature_cities.impl.data.Repository
import javax.inject.Inject

class GetWeatherPredictForCoordinate @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(location: Location): PlaceAndWeather =
        repository.getWeatherPredictForCoordinate(location)
}