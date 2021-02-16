package ru.thstdio.mypetopenweather.presentation.city

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.thstdio.mypetopenweather.data.convertor.toDomainLocation
import ru.thstdio.mypetopenweather.domain.Place
import ru.thstdio.mypetopenweather.domain.PlaceWeather
import ru.thstdio.mypetopenweather.domain.Weather
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(private val useCase: CityUseCase) : ViewModel(),
    PlaceHolderAction {

    private val _weatherCard: MutableLiveData<Pair<Place, Weather>> = MutableLiveData()
    val weatherCard: LiveData<Pair<Place, Weather>> get() = _weatherCard
    var weatherCardWithAnimation = false
        private set

    private val _city: MutableLiveData<String> = MutableLiveData()
    val city: LiveData<String> get() = _city

    private val _places: MutableLiveData<List<PlaceWeather>> = MutableLiveData()
    val places: LiveData<List<PlaceWeather>> get() = _places

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        Log.e("SearchCityViewModel", exception.toString())
    }

    init {
        //todo инит плохо но и
        //        liveData {
        //            useCase.getPlaces().collect { placesFromBd -> emit(placesFromBd) }
        //        }
        // тоже не могу использовать, так как я подгружаю погоду и записываю ее  liveda и тогда я потеряю связь с базой
        viewModelScope.launch(exceptionHandler) {
            useCase.getPlaces().collect { placesFromBd ->
                Log.e("SearchCityViewModel", "${placesFromBd.size}")
                _places.value = placesFromBd
                if (weatherCard.value == null && placesFromBd.isNotEmpty()) {
                    updateWeatherCardForPlace(placesFromBd.first().first,false)
                }
            }
        }
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
        updateWeatherCardForPlace(place)
    }

    private fun updateWeatherCardForPlace(place: Place,withAnim:Boolean=true) {
        viewModelScope.launch(exceptionHandler) {
            weatherCardWithAnimation = withAnim
            _weatherCard.value = place to useCase.getWeatherByPlace(place)
        }
    }

    override fun updateWeather(place: Place) {
        //todo не нравится кусок кода , паджинг не использую
        // т.к при при повторном поиске того или иного города он перемещается наверх
        viewModelScope.launch(exceptionHandler) {
            val weather = useCase.getWeatherByPlace(place)
            _places.value = _places.value?.map { placeAndWeather ->
                if (placeAndWeather.first == place) {
                    place to weather
                } else {
                    placeAndWeather
                }
            }
        }
    }

    override fun weatherCardUpdated() {
        weatherCardWithAnimation = false
    }

    //todo как лучше конвертировать здесь или уже с фрагмента передавать сконвертированные данные?
    fun updateCurrentLocation(location: android.location.Location) {
        val currentLocation = location.toDomainLocation()
        viewModelScope.launch {
            weatherCardWithAnimation = true
            _weatherCard.value = useCase.getWeatherByCoordinate(currentLocation)
        }
    }

    fun onClickToMap() {
        weatherCard.value?.let { (place, _) -> useCase.navigateToMap(place) }
        weatherCardWithAnimation = false
    }

}