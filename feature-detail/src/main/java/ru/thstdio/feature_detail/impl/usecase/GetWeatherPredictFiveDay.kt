package ru.thstdio.feature_detail.impl.usecase

import kotlinx.coroutines.flow.Flow
import ru.thstdio.core.domain.PredictForFiveDay
import ru.thstdio.feature_detail.impl.data.Repository
import javax.inject.Inject

class GetWeatherPredictFiveDay @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(placeId: Long): Flow<PredictForFiveDay> =
        repository.getWeatherPredictFiveDay(placeId)
}