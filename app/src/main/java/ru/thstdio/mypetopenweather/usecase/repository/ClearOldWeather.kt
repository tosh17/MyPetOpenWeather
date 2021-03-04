package ru.thstdio.mypetopenweather.usecase.repository

import ru.thstdio.mypetopenweather.data.Repository
import javax.inject.Inject

class ClearOldWeather @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() {
        return repository.clearOldWeather()
    }
}