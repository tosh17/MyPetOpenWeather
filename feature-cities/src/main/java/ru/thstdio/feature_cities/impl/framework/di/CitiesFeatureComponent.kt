package ru.thstdio.feature_cities.impl.framework.di

import com.example.core.di.general.PerFeature
import dagger.Component
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