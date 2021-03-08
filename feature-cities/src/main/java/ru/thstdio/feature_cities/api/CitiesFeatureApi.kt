package ru.thstdio.feature_cities.api

import ru.thstdio.module_injector.BaseAPI

interface CitiesFeatureApi : BaseAPI {
    fun citiesStarter(): CitiesStarter
}