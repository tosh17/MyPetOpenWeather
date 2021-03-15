package ru.thstdio.feature_cities.impl.framework.di

import dagger.Binds
import dagger.Module
import ru.thstdio.core.di.PerFeature
import ru.thstdio.feature_cities.api.CitiesStarter
import ru.thstdio.feature_cities.impl.start.CitiesStarterImpl

@Module
internal abstract class CitiesFeatureModule {
    @PerFeature
    @Binds
    abstract fun provideCitiesStarter(citiesStarter: CitiesStarterImpl): CitiesStarter

}