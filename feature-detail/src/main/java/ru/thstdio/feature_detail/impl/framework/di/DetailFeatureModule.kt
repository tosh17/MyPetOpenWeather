package ru.thstdio.feature_detail.impl.framework.di

import dagger.Binds
import dagger.Module
import ru.thstdio.core.di.PerFeature
import ru.thstdio.feature_detail.api.DetailStart
import ru.thstdio.feature_detail.impl.start.DetailStartImpl

@Module
abstract class DetailFeatureModule {
    @PerFeature
    @Binds
    abstract fun provideCitiesStarter(citiesStarter: DetailStartImpl): DetailStart
}