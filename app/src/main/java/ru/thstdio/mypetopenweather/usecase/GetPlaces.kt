package ru.thstdio.mypetopenweather.usecase

import kotlinx.coroutines.flow.Flow
import ru.thstdio.mypetopenweather.data.Repository
import ru.thstdio.mypetopenweather.domain.Place
import javax.inject.Inject

class GetPlaces @Inject constructor(private val repository: Repository) {
     operator fun invoke(): Flow<List<Place>> = repository.getAllPlaced()
}