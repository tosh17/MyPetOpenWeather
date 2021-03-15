package ru.thstdio.feature_map.impl.framework.di

import dagger.Component
import ru.thstdio.core.di.PerFeature
import ru.thstdio.feature_map.api.MapFeatureApi
import ru.thstdio.feature_map.impl.presentation.MapFragment

@Component(
    modules = [MapFeatureModule::class, ApiModule::class],
    dependencies = [MapFeatureDependencies::class]
)
@PerFeature
abstract class MapFeatureComponent : MapFeatureApi {
    abstract fun inject(fragment: MapFragment)

    companion object {
        fun initAndGet(citiesFeatureDependencies: MapFeatureDependencies): MapFeatureComponent {
            return DaggerMapFeatureComponent.builder()
                .mapFeatureDependencies(citiesFeatureDependencies)
                .build()
        }
    }
}