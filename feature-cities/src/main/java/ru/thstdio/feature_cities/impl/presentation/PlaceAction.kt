package ru.thstdio.feature_cities.impl.presentation

import ru.thstdio.core.domain.Place


interface PlaceHolderAction {
    fun onClickPlace(place: Place)
    fun updateWeather(place: Place)
    fun weatherCardUpdated()
}