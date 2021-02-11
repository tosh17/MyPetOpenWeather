package ru.thstdio.mypetopenweather.usecase

import ru.thstdio.mypetopenweather.data.Repository
import ru.thstdio.mypetopenweather.domain.Place
import ru.thstdio.mypetopenweather.domain.Weather
import javax.inject.Inject

class GetWeatherPredictForCity @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(city:String): Pair<Place,Weather> = repository.getWeatherPredictForCity(city)
}