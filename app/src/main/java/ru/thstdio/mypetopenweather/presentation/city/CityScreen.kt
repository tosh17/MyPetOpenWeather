package ru.thstdio.mypetopenweather.presentation.city

import com.github.terrakok.cicerone.androidx.FragmentScreen

class CityScreen : FragmentScreen(fragmentCreator = {
    CityFragment.newInstance()
})