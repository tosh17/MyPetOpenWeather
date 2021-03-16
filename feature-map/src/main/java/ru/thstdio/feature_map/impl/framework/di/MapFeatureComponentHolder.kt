package ru.thstdio.feature_map.impl.framework.di

import android.util.Log
import ru.thstdio.feature_map.api.MapFeatureApi
import ru.thstdio.module_injector.ComponentHolder

object MapFeatureComponentHolder : ComponentHolder<MapFeatureApi, MapFeatureDependencies> {
    private var mapFeatureComponent: MapFeatureComponent? = null
    private val TAG = "MapFeatureComponentHolder"

    override fun init(dependencies: MapFeatureDependencies) {
        Log.i(TAG, "init()")
        if (mapFeatureComponent == null) {
            synchronized(MapFeatureComponentHolder::class.java) {
                if (mapFeatureComponent == null) {
                    Log.i(TAG, "initAndGet()")
                    mapFeatureComponent = MapFeatureComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): MapFeatureApi = getComponent()

    internal fun getComponent(): MapFeatureComponent {
        checkNotNull(mapFeatureComponent) { "MapFeatureComponent was not initialized!" }
        return mapFeatureComponent!!
    }

    override fun reset() {
        Log.i(TAG, "reset()")
        mapFeatureComponent = null
    }

}