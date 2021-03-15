package ru.thstdio.feature_cities.impl.presentation

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.thstdio.core.domain.Place
import ru.thstdio.core.domain.PlaceAndWeather
import ru.thstdio.core.navigation.AppRouter
import ru.thstdio.core.navigation.Features
import ru.thstdio.feature_cities.impl.data.convertor.toDomainLocation


class CityViewModel(
    private val useCase: CityUseCase,
    private val navigation: AppRouter
) : ViewModel(), PlaceHolderAction {

    private val _weatherCard: MutableLiveData<PlaceAndWeather> = MutableLiveData()
    val weatherCard: LiveData<PlaceAndWeather> get() = _weatherCard
    var weatherCardWithAnimation = false
        private set

    private val _city: MutableLiveData<String> = MutableLiveData()
    val city: LiveData<String> get() = _city

    private val _places: MutableLiveData<List<PlaceAndWeather>> = liveData {
        try {
            useCase.getPlaces().collect { placesFromBd ->
                emit(placesFromBd)
                if (weatherCard.value == null && placesFromBd.isNotEmpty()) {
                    updateWeatherCardForPlace(placesFromBd.first().place, false)
                }
            }
        } catch (e: Exception) {
            Log.e("SearchCityViewModel", e.toString())
        }

    } as MutableLiveData<List<PlaceAndWeather>>
    val places: LiveData<List<PlaceAndWeather>> get() = _places

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("SearchCityViewModel", exception.toString())
    }

    fun findCityWeather() {
        viewModelScope.launch(exceptionHandler) {
            if (!_city.value.isNullOrEmpty()) {
                weatherCardWithAnimation = true
                _weatherCard.value = useCase.getWeatherByCityName(_city.value!!)
            }
        }
    }

    fun setCity(text: String) {
        _city.value = text
    }

    override fun onClickPlace(place: Place) {
        viewModelScope.launch(exceptionHandler) { useCase.setPlaceToTop(place) }
        updateWeatherCardForPlace(place)
    }

    private fun updateWeatherCardForPlace(place: Place, withAnim: Boolean = true) {
        viewModelScope.launch(exceptionHandler) {
            weatherCardWithAnimation = withAnim
            _weatherCard.value = PlaceAndWeather(place, useCase.getWeatherByPlace(place))
        }
    }

    override fun updateWeather(place: Place) {
        viewModelScope.launch(exceptionHandler) {
            val weather = useCase.getWeatherByPlace(place)
            _places.value = _places.value?.map { placeAndWeather ->
                if (placeAndWeather.place == place) {
                    PlaceAndWeather(place, weather)
                } else {
                    placeAndWeather
                }
            }
        }
    }

    override fun weatherCardUpdated() {
        weatherCardWithAnimation = false
    }

    fun updateCurrentLocation(location: android.location.Location) {
        val currentLocation = location.toDomainLocation()
        viewModelScope.launch {
            weatherCardWithAnimation = true
            _weatherCard.value = useCase.getWeatherByCoordinate(currentLocation)
        }
    }

    fun onClickToMap() {
        weatherCard.value?.let { (place, _) -> navigation.openFeature(Features.Map(place)) }
        weatherCardWithAnimation = false
    }

    fun onClickToDetail() {
        weatherCard.value?.let { (place, _) ->
            navigation.openFeature(Features.Detail(place))
        }
        weatherCardWithAnimation = false
    }

}