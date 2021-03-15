package ru.thstdio.feature_cities.impl.usecase

import ru.thstdio.core.domain.Place
import ru.thstdio.feature_cities.impl.data.Repository
import javax.inject.Inject

class SetPlaceToTop @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(place: Place) {
        repository.setPlaceToTop(place.cityId)

    }
}