package ru.thstdio.feature_detail.impl.framework.di

import dagger.Component
import ru.thstdio.feature_detail.api.DetailFeatureApi
import ru.thstdio.feature_detail.impl.presentation.DetailFragment
import ru.thstdio.module_injector.di.PerFeature

@Component(
    modules = [DetailFeatureModule::class, ApiModule::class],
    dependencies = [DetailFeatureDependencies::class]
)
@PerFeature
abstract class DetailFeatureComponent : DetailFeatureApi {
    abstract fun inject(fragment: DetailFragment)

    companion object {
        fun initAndGet(DetailFeatureDependencies: DetailFeatureDependencies): DetailFeatureComponent {
            return DaggerDetailFeatureComponent.builder()
                .detailFeatureDependencies(DetailFeatureDependencies)
                .build()

        }
    }
}