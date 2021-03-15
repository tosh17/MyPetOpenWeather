package ru.thstdio.feature_map.impl.usecase

import kotlinx.coroutines.flow.Flow
import ru.thstdio.core.domain.PlaceAndWeather
import ru.thstdio.feature_map.impl.data.Repository
import javax.inject.Inject

class GetPlacesWithLoadedWeather @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): Flow<List<PlaceAndWeather>> {
        return repository.getAllPlacedWithLoadedWeather()
    }
}