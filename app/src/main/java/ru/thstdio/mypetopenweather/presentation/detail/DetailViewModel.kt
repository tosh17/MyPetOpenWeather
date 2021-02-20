package ru.thstdio.mypetopenweather.presentation.detail

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    useCase: DetailUserCase,
    handle: SavedStateHandle
) : ViewModel(), OnWeatherItemClickListener {

    private val _placeName: MutableLiveData<String> = MutableLiveData()
    val placeName: LiveData<String> = _placeName

    private val _weathers: MutableLiveData<List<WeatherHolderItem>> = MutableLiveData()
    val weathers: LiveData<List<WeatherHolderItem>> = _weathers

    private val _selectedWeather: MutableLiveData<WeatherHolderItem> = MutableLiveData()
    val selectedWeather: LiveData<WeatherHolderItem> = _selectedWeather

    init {
        val cityId = handle.get<Long>(PLACE_ID_ARG)
        cityId?.let { id ->
            viewModelScope.launch {
                val (place, listWeather) = useCase.getPredict(id)
                _placeName.value = place.name
                _weathers.value = listWeather.mapIndexed { index, item ->
                    WeatherHolderItem(
                        isActive = index == 0,
                        weather = item
                    )
                }
                if (listWeather.isNotEmpty()) {
                    _selectedWeather.value = weathers.value?.first()
                }
            }
        }

    }

    override fun onClickItem(item: WeatherHolderItem) {
        _weathers.value = _weathers.value?.map { weatherHolderItem ->
            when {
                weatherHolderItem.weather.date == item.weather.date -> item.copy(isActive = true)
                weatherHolderItem.isActive -> weatherHolderItem.copy(isActive = false)
                else -> weatherHolderItem
            }
        }
        _selectedWeather.value = item
    }
}