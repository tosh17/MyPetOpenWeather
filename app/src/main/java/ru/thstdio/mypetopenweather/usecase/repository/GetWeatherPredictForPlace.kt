package ru.thstdio.mypetopenweather.usecase.repository

import ru.thstdio.mypetopenweather.data.Repository
import ru.thstdio.mypetopenweather.domain.Place
import ru.thstdio.mypetopenweather.domain.Weather
import javax.inject.Inject

class GetWeatherPredictForPlace @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(place: Place): Weather = repository.getWeatherPredictForPlace(place)
}