package ru.thstdio.mypetopenweather.usecase.repository

import ru.thstdio.mypetopenweather.data.Repository
import ru.thstdio.mypetopenweather.domain.Location
import ru.thstdio.mypetopenweather.domain.PlaceAndWeather
import javax.inject.Inject

class GetWeatherPredictForCoordinate @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(location: Location): PlaceAndWeather =
        repository.getWeatherPredictForCoordinate(location)
}