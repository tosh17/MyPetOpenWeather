package ru.thstdio.feature_cities.impl.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.thstdio.core.domain.PlaceAndWeather
import ru.thstdio.feature_cities.impl.data.Repository
import javax.inject.Inject

class GetPlaces @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<List<PlaceAndWeather>> {
        return repository.getAllPlaced()
            .map { list -> list.map { item -> PlaceAndWeather(item, null) } }
    }
}