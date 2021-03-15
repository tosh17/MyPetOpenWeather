package ru.thstdio.feature_map.api

import ru.thstdio.module_injector.BaseAPI

interface MapFeatureApi : BaseAPI {
    fun mapStarter(): MapStarter
}