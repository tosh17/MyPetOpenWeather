package ru.thstdio.feature_detail.impl.presentation

import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.thstdio.core.domain.PredictForFiveDay
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val useCase: DetailUserCase
) : ViewModel(), OnWeatherItemClickListener {


    private val _predict: MutableLiveData<PredictForFiveDay> = MutableLiveData()
    val predict: LiveData<PredictForFiveDay> get() = _predict
    val placeName: LiveData<String> get() = _predict.map { (place, _) -> place.name }

    private val _weathers: MutableLiveData<List<WeatherHolderItem>> = MutableLiveData()
    val weathers: LiveData<List<WeatherHolderItem>> = _weathers

    private val _selectedWeather: MutableLiveData<WeatherHolderItem> = MutableLiveData()
    val selectedWeather: LiveData<WeatherHolderItem> = _selectedWeather

    fun setCityId(cityId: Long) {
        viewModelScope.launch {
            if (cityId != 0L) {
                useCase.getPredict(cityId).collect { predict ->
                    _predict.value = predict
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