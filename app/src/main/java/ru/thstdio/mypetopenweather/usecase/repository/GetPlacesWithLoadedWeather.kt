package ru.thstdio.mypetopenweather.usecase.repository

import kotlinx.coroutines.flow.Flow
import ru.thstdio.mypetopenweather.data.Repository
import ru.thstdio.mypetopenweather.domain.PlaceWeather
import javax.inject.Inject

class GetPlacesWithLoadedWeather @Inject constructor(private val repository: Repository) {
   suspend operator fun invoke(): Flow<List<PlaceWeather>> {
        return repository.getAllPlacedWithLoadedWeather()
    }
}