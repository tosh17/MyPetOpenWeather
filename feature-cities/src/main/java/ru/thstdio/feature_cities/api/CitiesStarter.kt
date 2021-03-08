package ru.thstdio.feature_cities.api

import com.github.terrakok.cicerone.Router
import ru.thstdio.core.navigation.AppRouter

interface CitiesStarter {
    fun start(navigator: AppRouter<Router>)
}