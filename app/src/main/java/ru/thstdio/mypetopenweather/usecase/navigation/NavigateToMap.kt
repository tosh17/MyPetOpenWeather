package ru.thstdio.mypetopenweather.usecase.navigation

import ru.thstdio.mypetopenweather.data.Repository
import ru.thstdio.mypetopenweather.domain.Place
import ru.thstdio.mypetopenweather.domain.Weather
import ru.thstdio.mypetopenweather.framework.navigation.AppNavigation
import ru.thstdio.mypetopenweather.presentation.map.MapScreen
import javax.inject.Inject

class NavigateToMap@Inject constructor(private val navigation:AppNavigation) {
    operator fun invoke(place: Place) {
        navigation.router.navigateTo(MapScreen(place))
    }
}

class GetWeatherPredictForPlace @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(place: Place): Weather = repository.getWeatherPredictForPlace(place)
}