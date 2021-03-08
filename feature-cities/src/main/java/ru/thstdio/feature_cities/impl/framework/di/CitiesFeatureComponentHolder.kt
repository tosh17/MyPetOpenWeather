package ru.thstdio.feature_cities.impl.framework.di

import android.util.Log
import ru.thstdio.feature_cities.api.CitiesFeatureApi
import ru.thstdio.module_injector.ComponentHolder

object CitiesFeatureComponentHolder : ComponentHolder<CitiesFeatureApi, CitiesFeatureDependencies> {
    private var citiesFeatureComponent: CitiesFeatureComponent? = null
    private val TAG = "CityFeatureComponentHolder"

    override fun init(dependencies: CitiesFeatureDependencies) {
        Log.i(TAG, "init()")
        if (citiesFeatureComponent == null) {
            synchronized(CitiesFeatureComponentHolder::class.java) {
                if (citiesFeatureComponent == null) {
                    Log.i(TAG, "initAndGet()")
                    citiesFeatureComponent = CitiesFeatureComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): CitiesFeatureApi = getComponent()

    internal fun getComponent(): CitiesFeatureComponent {
        checkNotNull(citiesFeatureComponent) { "CityFeatureComponent was not initialized!" }
        return citiesFeatureComponent!!
    }

    override fun reset() {
        Log.i(TAG, "reset()")
        citiesFeatureComponent = null
    }

}