package ru.thstdio.mypetopenweather.presentation.city

import ru.thstdio.mypetopenweather.domain.Place

interface PlaceHolderAction {
    fun onClickPlace(place: Place)
    fun updateWeather(place: Place)
    fun weatherCardUpdated()
}