package ru.thstdio.feature_map.impl.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.collect
import ru.thstdio.core.domain.PlaceAndWeather
import ru.thstdio.core.navigation.AppRouter


class MapViewModel(
    private val useCase: MapUseCase,
    private val navigation: AppRouter
) : ViewModel() {

    val places: LiveData<List<PlaceAndWeather>> = liveData {
        useCase.getPlaces().collect { list ->
            emit(list)
        }
    }
}