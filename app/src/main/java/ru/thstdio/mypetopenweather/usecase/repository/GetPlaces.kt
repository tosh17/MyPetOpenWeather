package ru.thstdio.mypetopenweather.usecase.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.thstdio.mypetopenweather.data.Repository
import ru.thstdio.mypetopenweather.domain.PlaceAndWeather
import javax.inject.Inject

class GetPlaces @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<List<PlaceAndWeather>> {
        return repository.getAllPlaced()
            .map { list -> list.map { item -> PlaceAndWeather(item, null) } }
    }
}