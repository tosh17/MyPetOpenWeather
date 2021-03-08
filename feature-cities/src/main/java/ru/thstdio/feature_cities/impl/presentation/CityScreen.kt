package ru.thstdio.feature_cities.impl.presentation

import com.github.terrakok.cicerone.androidx.FragmentScreen

class CityScreen : FragmentScreen(fragmentCreator = {
    CityFragment.newInstance()
})