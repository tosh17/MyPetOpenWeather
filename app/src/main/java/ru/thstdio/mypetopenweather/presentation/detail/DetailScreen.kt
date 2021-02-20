package ru.thstdio.mypetopenweather.presentation.detail

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.thstdio.mypetopenweather.domain.Place

class DetailScreen(place: Place) : FragmentScreen(fragmentCreator = {
    DetailFragment.newInstance(place)
})