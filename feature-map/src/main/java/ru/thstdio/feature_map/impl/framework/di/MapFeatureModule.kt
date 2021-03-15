package ru.thstdio.feature_map.impl.framework.di

import dagger.Binds
import dagger.Module
import ru.thstdio.core.di.PerFeature
import ru.thstdio.feature_map.api.MapStarter
import ru.thstdio.feature_map.impl.start.MapStarterImpl

@Module
internal abstract class MapFeatureModule {
    @PerFeature
    @Binds
    abstract fun provideMapStarter(starter: MapStarterImpl): MapStarter
}