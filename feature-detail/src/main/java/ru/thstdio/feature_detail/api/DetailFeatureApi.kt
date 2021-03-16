package ru.thstdio.feature_detail.api

import ru.thstdio.module_injector.BaseAPI

interface DetailFeatureApi : BaseAPI {
    fun detailStarter(): DetailStart
}