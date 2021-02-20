package ru.thstdio.mypetopenweather.usecase.repository

import ru.thstdio.mypetopenweather.data.Repository
import ru.thstdio.mypetopenweather.domain.Place
import javax.inject.Inject

class SetPlaceToTop @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(place: Place) {
        repository.setPlaceToTop(place.cityId)

    }
}