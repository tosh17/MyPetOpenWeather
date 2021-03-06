package ru.thstdio.mypetopenweather.presentation.city

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.thstdio.mypetopenweather.data.convertor.toDomainLocation
import ru.thstdio.mypetopenweather.domain.Place
import ru.thstdio.mypetopenweather.domain.PlaceAndWeather
import ru.thstdio.mypetopenweather.framework.navigation.AppNavigation
import ru.thstdio.mypetopenweather.presentation.detail.DetailScreen
import ru.thstdio.mypetopenweather.presentation.map.MapScreen
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val useCase: CityUseCase,
    private val navigation: AppNavigation
) : ViewModel(),
    PlaceHolderAction {

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

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
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
        weatherCard.value?.let { (place, _) -> navigation.router.navigateTo(MapScreen(place)) }
        weatherCardWithAnimation = false
    }

    fun onClickToDetail() {
        weatherCard.value?.let { (place, _) -> navigation.router.navigateTo(DetailScreen(place)) }
        weatherCardWithAnimation = false
    }

}