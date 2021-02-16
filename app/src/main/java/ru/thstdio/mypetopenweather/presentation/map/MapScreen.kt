package ru.thstdio.mypetopenweather.presentation.map

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.thstdio.mypetopenweather.domain.Place

class MapScreen(place: Place) : FragmentScreen(fragmentCreator = {
    MapFragment.newInstance(place)
})