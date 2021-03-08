package ru.thstdio.feature_cities.impl.start

import com.example.core.di.general.PerFeature
import com.github.terrakok.cicerone.Router
import ru.thstdio.core.navigation.AppRouter
import ru.thstdio.feature_cities.api.CitiesStarter
import ru.thstdio.feature_cities.impl.presentation.CityScreen
import javax.inject.Inject

@PerFeature
class CitiesStarterImpl @Inject constructor() : CitiesStarter {
    override fun start(navigator: AppRouter<Router>) {
        navigator.getRouter().newRootScreen(CityScreen())
    }
}