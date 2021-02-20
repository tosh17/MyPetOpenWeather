package ru.thstdio.mypetopenweather.usecase.repository

import ru.thstdio.mypetopenweather.data.Repository
import ru.thstdio.mypetopenweather.domain.Place
import ru.thstdio.mypetopenweather.domain.WeatherWithDate
import javax.inject.Inject

class GetWeatherPredictFiveDay @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(placeId: Long): Pair<Place, List<WeatherWithDate>> =
        repository.getWeatherPredictFiveDay(placeId)
}