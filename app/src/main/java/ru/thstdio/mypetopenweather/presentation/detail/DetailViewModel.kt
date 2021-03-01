package ru.thstdio.mypetopenweather.presentation.detail

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import ru.thstdio.mypetopenweather.domain.PredictForFiveDay
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    useCase: DetailUserCase,
    handle: SavedStateHandle
) : ViewModel(), OnWeatherItemClickListener {

    private val _predict: LiveData<PredictForFiveDay> = liveData {
        val cityId = handle.get<Long>(PLACE_ID_ARG)
        if (cityId != 0) {
            useCase.getPredict(cityId!!).collect { predict ->
                emit(predict)
                _weathers.value = predict.listWeather.mapIndexed { index, item ->
                    WeatherHolderItem(
                        isActive = index == 0,
                        weather = item
                    )
                }
                if (predict.listWeather.isNotEmpty()) {
                    _selectedWeather.value = weathers.value?.first()
                }
            }
        } else {
            error("DetailViewModel cityId not be null")
        }

    }
    val placeName: LiveData<String> get() = _predict.map { (place, _) -> place.name }

    private val _weathers: MutableLiveData<List<WeatherHolderItem>> = MutableLiveData()
    val weathers: LiveData<List<WeatherHolderItem>> = _weathers

    private val _selectedWeather: MutableLiveData<WeatherHolderItem> = MutableLiveData()
    val selectedWeather: LiveData<WeatherHolderItem> = _selectedWeather

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