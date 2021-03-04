package ru.thstdio.mypetopenweather.usecase.repository

import kotlinx.coroutines.flow.Flow
import ru.thstdio.mypetopenweather.data.Repository
import ru.thstdio.mypetopenweather.domain.PredictForFiveDay
import javax.inject.Inject

class GetWeatherPredictFiveDay @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(placeId: Long): Flow<PredictForFiveDay> =
        repository.getWeatherPredictFiveDay(placeId)
}