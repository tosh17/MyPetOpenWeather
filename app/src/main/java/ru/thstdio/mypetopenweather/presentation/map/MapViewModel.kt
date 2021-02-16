package ru.thstdio.mypetopenweather.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import ru.thstdio.mypetopenweather.domain.PlaceWeather
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val useCase: MapUseCase) : ViewModel() {
    val places: LiveData<List<PlaceWeather>>
        get() = liveData<List<PlaceWeather>> {
            useCase.getPlaces().collect { list ->
                emit(list)
            }
        }
}