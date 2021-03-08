package ru.thstdio.feature_cities.impl.framework.di

import com.github.terrakok.cicerone.Router
import retrofit2.Retrofit
import ru.thstdio.core.navigation.AppRouter
import ru.thstdio.core_db.api.data.DbClient
import ru.thstdio.module_injector.BaseDependencies

interface CitiesFeatureDependencies : BaseDependencies {
    fun dbClient(): DbClient
    fun httpClient(): Retrofit
    fun globalNavigator(): AppRouter<Router>
}