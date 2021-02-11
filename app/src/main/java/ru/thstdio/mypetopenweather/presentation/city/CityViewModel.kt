package ru.thstdio.mypetopenweather.presentation.city

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.thstdio.mypetopenweather.domain.Place
import ru.thstdio.mypetopenweather.domain.Weather

class CityViewModel(private val useCase: CityUseCase) : ViewModel() {

    private val _weather: MutableLiveData<Pair<Place,Weather>> = MutableLiveData()
    val weather: LiveData<Pair<Place,Weather>> get() = _weather

    private val _city: MutableLiveData<String> = MutableLiveData()
    val city: LiveData<String> get() = _city

    private val _places: MutableLiveData<List<Place>> = MutableLiveData()
    val places: LiveData<List<Place>> get() = _places

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        Log.e("SearchCityViewModel", exception.toString())
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            useCase.getPlaces().collect { placesFromBd ->
                Log.e("SearchCityViewModel", "${placesFromBd.size}")
                _places.value = placesFromBd
            }
        }
    }

    fun getWeather() {
        viewModelScope.launch(exceptionHandler) {
            if (!_city.value.isNullOrEmpty()) {
                _weather.value = useCase.getWeatherByCityName(_city.value!!)
            }
        }
    }

    fun setCity(text: String) {
        _city.value = text
    }
}