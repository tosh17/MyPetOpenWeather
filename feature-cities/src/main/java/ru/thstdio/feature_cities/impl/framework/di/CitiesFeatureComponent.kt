package ru.thstdio.feature_cities.impl.framework.di

import dagger.Component
import ru.thstdio.core.di.PerFeature
import ru.thstdio.feature_cities.api.CitiesFeatureApi
import ru.thstdio.feature_cities.impl.presentation.CityFragment

@Component(
    modules = [CitiesFeatureModule::class, ApiModule::class],
    dependencies = [CitiesFeatureDependencies::class]
)
@PerFeature
internal abstract class CitiesFeatureComponent : CitiesFeatureApi {
    abstract fun inject(fragment: CityFragment)

    companion object {
        fun initAndGet(citiesFeatureDependencies: CitiesFeatureDependencies): CitiesFeatureComponent {
            return DaggerCitiesFeatureComponent.builder()
                .citiesFeatureDependencies(citiesFeatureDependencies)
                .build()

        }
    }
}