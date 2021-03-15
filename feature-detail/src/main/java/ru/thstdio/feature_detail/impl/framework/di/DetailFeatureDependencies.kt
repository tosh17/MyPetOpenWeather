package ru.thstdio.feature_detail.impl.framework.di

import retrofit2.Retrofit
import ru.thstdio.core.navigation.AppRouter
import ru.thstdio.core_db.api.data.DbClient
import ru.thstdio.module_injector.BaseDependencies

interface DetailFeatureDependencies : BaseDependencies {
    fun dbClient(): DbClient
    fun httpClient(): Retrofit
    fun globalNavigator(): AppRouter
}